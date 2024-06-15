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
import me.intuit.cat.data.repository.BreedsRepositoryImpl.Companion.DEFAULT_PAGE_INDEX
import me.intuit.cat.domain.util.onSuccess
import java.io.IOException
import java.io.InvalidObjectException
import javax.inject.Inject


private const val MOVIE_STARTING_PAGE_INDEX = 1
@OptIn(ExperimentalPagingApi::class)
class BreedsRemoteMediator @Inject constructor(
    private val remote: BreedsDataSource.Remote,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, BreedImageEntity>() {

/*    override suspend fun load(loadType: LoadType, state: PagingState<Int, BreedEntity>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> MOVIE_STARTING_PAGE_INDEX
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> local.getLastRemoteKey()?.nextPage ?: return MediatorResult.Success(endOfPaginationReached = true)
        }

      //  Log.d("XXX", "BreedsRemoteMediator: load() called with: loadType = $loadType, page: $page, stateLastItem = ${state.isEmpty()}")

        // There was a lag in loading the first page; as a result, it jumps to the end of the pagination.
        if (state.isEmpty() && page == 2) return MediatorResult.Success(endOfPaginationReached = false)

        remote.getBreeds(page, state.config.pageSize).getResult({ successResult ->
            Log.d("XXX", "BreedsRemoteMediator: get movies from remote")
            if (loadType == LoadType.REFRESH) {
                local.clearBreeds()
                local.clearRemoteKeys()
            }

            val breeds = successResult.data

            val endOfPaginationReached = breeds.isEmpty()

            val prevPage = if (page == MOVIE_STARTING_PAGE_INDEX) null else page - 1
            val nextPage = if (endOfPaginationReached) null else page + 1

            val key = BreedRemoteKeyDbData(prevPage = prevPage, nextPage = nextPage)

            local.insertBreed(breeds)
            local.saveRemoteKey(key)

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }, { errorResult ->
            return MediatorResult.Error(errorResult.error)
        })
    }*/

  @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
  override suspend fun load(
        loadType: LoadType, state: PagingState<Int, BreedImageEntity>
    ): MediatorResult {

        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

      try {
           // remote.getBreeds(page, state.config.pageSize).getResult
           // val response = doggoApiService.getDoggoImages(page, state.config.pageSize)
            val response = remote.getBreedsImages(page, state.config.pageSize)
            val keys:ArrayList<BreedRemoteKeyDbData> =  arrayListOf()
            val breedsEntity:ArrayList<BreedImageEntity> =  arrayListOf()
          response.onSuccess {
              breedsEntity.addAll(it.toBreedEntityList())

          }


            val isEndOfList = breedsEntity.isEmpty()

            appDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    appDatabase.breedsRemoteKeyDao() .clearRemoteKeys()
                    appDatabase.breedDao().deleteAll()
                }

                    response.onSuccess {
                        it.toBreedEntityList().forEach {
                        val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
                        val nextKey = if (isEndOfList) null else page + 1
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
        try {
            when (loadType) {
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
        catch (e:IOException) {
            var remoteKeys = BreedRemoteKeyDbData(0,DEFAULT_PAGE_INDEX,1)

        }
        return 0
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