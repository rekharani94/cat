package me.intuit.cat.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.intuit.cat.data.local.AppDatabase
import me.intuit.cat.data.local.entity.BreedImageEntity
import me.intuit.cat.data.local.entity.BreedRemoteKeyDbData
import me.intuit.cat.data.mapper.toBreedEntityList
import me.intuit.cat.data.repository.BreedsRepo.Companion.DEFAULT_PAGE_INDEX
import me.intuit.cat.domain.util.onSuccess
import java.io.IOException
import java.io.InvalidObjectException
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class BreedsRemoteMediator @Inject constructor(
    private val remote: BreedsDataSource.Remote,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, BreedImageEntity>() {

   override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
    return withContext(Dispatchers.IO) {
          if (System.currentTimeMillis() - (appDatabase.breedsRemoteKeyDao().getCreationTime() ?: 0) < cacheTimeout) {
            return@withContext InitializeAction.SKIP_INITIAL_REFRESH
         } else {
            return@withContext InitializeAction.LAUNCH_INITIAL_REFRESH
         }
     }

    }
  @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
  override suspend fun load(
        loadType: LoadType, state: PagingState<Int, BreedImageEntity>
    ): MediatorResult {

      val page: Int = when (loadType) {
          LoadType.REFRESH -> {
              val remoteKeys = getClosestRemoteKey(state)
              remoteKeys?.nextPage?.minus(1) ?: 1
          }
          LoadType.PREPEND -> {
              val remoteKeys = getFirstRemoteKey(state)
              val prevKey = remoteKeys?.prevPage
              prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
          }
          LoadType.APPEND -> {
              val remoteKeys = getLastRemoteKey(state)
              val nextKey = remoteKeys?.nextPage
              nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
          }
      }


      try {
            val response = remote.getBreedsImages(page, state.config.pageSize)
            val keys:ArrayList<BreedRemoteKeyDbData> =  arrayListOf()
            val breedsEntity:ArrayList<BreedImageEntity> =  arrayListOf()
          response.onSuccess {
              breedsEntity.addAll(it.toBreedEntityList(page))

          }


            val isEndOfList = breedsEntity.isEmpty()

            appDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    appDatabase.breedsRemoteKeyDao() .clearRemoteKeys()
                    appDatabase.breedDao().deleteAll()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (isEndOfList) null else page + 1
                response.onSuccess {
                    it.toBreedEntityList(page).map {
                        keys.add( BreedRemoteKeyDbData(id = it.rid.toInt(), prevPage  = prevKey, nextPage = nextKey))

                    }
                }


                appDatabase.breedsRemoteKeyDao().insertAll(keys)
                appDatabase.breedImageDao() .insertAll(breedsEntity)
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    /**
     * this returns the page key or the final end of list success result
     */
    suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, BreedImageEntity>): Any? {

          return  when (loadType) {
                LoadType.REFRESH -> {
                    var remoteKeys = getClosestRemoteKey(state)
                    remoteKeys?.nextPage?.minus(1) ?: DEFAULT_PAGE_INDEX
                }

                LoadType.APPEND -> {
                    val remoteKeys = getLastRemoteKey(state)
                        ?: throw InvalidObjectException("Remote key should not be null for $loadType")
                    remoteKeys.nextPage
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getFirstRemoteKey(state)
                        ?: throw InvalidObjectException("Invalid state, key should not be null")
                    //end of list condition reached
                    remoteKeys?.prevPage
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    remoteKeys?.prevPage
                }
            }

    }

    /**
     * get the last remote key inserted which had the data
     */
    private suspend fun getLastRemoteKey(state: PagingState<Int, BreedImageEntity>): BreedRemoteKeyDbData? = withContext(Dispatchers.IO) {
        return@withContext  state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { breed -> appDatabase.breedsRemoteKeyDao().getRemoteKeyByMovieId(breed.rid) }
    }

    /**
     * get the first remote key inserted which had the data
     */
    private suspend fun getFirstRemoteKey(state: PagingState<Int, BreedImageEntity>): BreedRemoteKeyDbData? = withContext(Dispatchers.IO)  {
        return@withContext state.pages
            .firstOrNull() { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { breed -> appDatabase.breedsRemoteKeyDao().getRemoteKeyByMovieId(breed.rid) }
    }

    /**
     * get the closest remote key inserted which had the data
     */
    private suspend fun getClosestRemoteKey(state: PagingState<Int, BreedImageEntity>): BreedRemoteKeyDbData? = withContext(Dispatchers.IO) {
        return@withContext state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.rid?.let { repoId ->
                appDatabase.breedsRemoteKeyDao().getRemoteKeyByMovieId(repoId)
            }
        }
    }
}