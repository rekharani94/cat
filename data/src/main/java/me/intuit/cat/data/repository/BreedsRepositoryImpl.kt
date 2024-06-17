package me.intuit.cat.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import me.intuit.cat.data.api.NetworkService
import me.intuit.cat.data.local.AppDatabase
import me.intuit.cat.data.local.entity.mapToDomain
import me.intuit.cat.data.local.entity.toDomain
import me.intuit.cat.data.mapper.toDomainList
import me.intuit.cat.domain.model.Breed
import me.intuit.cat.domain.model.BreedImage
import me.intuit.cat.domain.repository.BreedsRepository
import javax.inject.Inject

@ExperimentalPagingApi
class BreedsRepositoryImpl @Inject constructor(
    val apiService: NetworkService,
    val remoteDataSource: BreedsDataSource.Remote,
    val appDatabase: AppDatabase,
    val localDataSource: BreedsDataSource.Local
): BreedsRepository {

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20

        //get doggo repository instance
    }

    /**
     * calling the paging source to give results from api calls
     * and returning the results in the form of flow [Flow<PagingData<DoggoImageModel>>]
     * since the [PagingDataAdapter] accepts the [PagingData] as the source in later stage
     */
    /*  override fun getBreeds(): Flow<PagingData<Breed>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { BreedPagingSource(apiService) }
        ).flow
    }*/
    @OptIn(ExperimentalPagingApi::class)
    override fun getBreedsImagesList(): Flow<PagingData<BreedImage>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { BreedPagingSource(apiService) }
        ).flow
    }


    /*//for rxjava users
    fun letDoggoImagesObservable(pagingConfig: PagingConfig = getDefaultPageConfig()): Observable<PagingData<DoggoImageModel>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { DoggoImagePagingSource(doggoApiService) }
        ).observable
    }
*/
    /* //for live data users
    fun letDoggoImagesLiveData(pagingConfig: PagingConfig = getDefaultPageConfig()): LiveData<PagingData<DoggoImageModel>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { DoggoImagePagingSource(doggoApiService) }
        ).liveData
    }*/

    /**
     * let's define page size, page size is the only required param, rest is optional
     */
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

    override  fun getBreedsImagesFromRemote(
        breedId: String,
        imageLimit: Int,
        size: String,
        memeTypes: String
    ): Flow<List<BreedImage>> =  flow{
        emit(apiService.getBreedImages(breedId,imageLimit,size,memeTypes).mapToDomain())
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



}