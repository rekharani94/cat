package me.intuit.cat.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.withContext
import me.intuit.cat.data.api.NetworkService
import me.intuit.cat.data.local.AppDatabase
import me.intuit.cat.data.local.entity.mapToDomain
import me.intuit.cat.data.local.entity.toDomain
import me.intuit.cat.data.mapper.toDomainList
import me.intuit.cat.domain.model.Breed
import me.intuit.cat.domain.model.BreedImage
import me.intuit.cat.domain.util.Result
import me.intuit.cat.domain.repository.BreedsRepository
import java.io.IOException
import javax.inject.Inject

@ExperimentalPagingApi
class BreedsRepositoryImpl @Inject constructor(
    val apiService: NetworkService,
    val remoteDataSource: BreedsDataSource.Remote,
    val appDatabase: AppDatabase,
): BreedsRepository {

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20

    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getBreedsImagesList(): Flow<PagingData<BreedImage>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { BreedPagingSource(apiService) }
        ).flow
    }


    fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getBreedsDirectlyFromDB(): Flow<PagingData<BreedImage>> =
        withContext(Dispatchers.IO) {
            if (appDatabase == null) throw IllegalStateException("Database is not initialized")

            val pagingSourceFactory = { appDatabase.breedImageDao().getAllImages() }
            return@withContext Pager(
                config = getDefaultPageConfig(),
                pagingSourceFactory = pagingSourceFactory,
                remoteMediator = BreedsRemoteMediator(remoteDataSource, appDatabase)
            ).flow.map { pagingData ->
                pagingData.map { it.toDomain() }
            }
        }

    override suspend fun getBreedsfromDB(): Flow<List<Breed>> = withContext(Dispatchers.IO){
        if (appDatabase == null) throw IllegalStateException("Database is not initialized")

        val breedsImagesList = appDatabase.breedDao().getAll()
        return@withContext {breedsImagesList.toDomainList()}.asFlow()
    }

    override fun getBreeds(imageId:String): Flow<List<Breed>> =flow{
        emit(apiService.getBreedImages(imageId).mapToDomain())
    }

    }


