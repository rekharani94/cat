package me.intuit.cat.data.repository

import androidx.paging.PagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.intuit.cat.data.exception.DataNotAvailableException
import me.intuit.cat.data.local.AppDatabase
import me.intuit.cat.data.local.entity.BreedImageEntity
import me.intuit.cat.data.local.entity.BreedRemoteKeyDbData
import me.intuit.cat.data.local.entity.toDomain
import me.intuit.cat.data.mapper.toBreedEntityList
import me.intuit.cat.data.mapper.toBreedImageEntity
import me.intuit.cat.domain.model.BreedImage
import me.intuit.cat.domain.util.Result
import javax.inject.Inject

class LocalDataSource  @Inject constructor (private val appDatabase: AppDatabase,

                                            ):BreedsDataSource.Local {

    override fun breedsImages(): PagingSource<Int, BreedImageEntity> {
        return  appDatabase.breedImageDao().getAllImages()
    }

    override suspend fun getBreedImages(): Result<List<BreedImage>> =
        withContext(Dispatchers.IO){
            val breeds = appDatabase.breedImageDao().getAll()
            return@withContext if (breeds.isNotEmpty()) {
                Result.Success(breeds.map { it.toDomain() })
            } else {
                Result.Error(DataNotAvailableException())
            }
        }


    override suspend fun insertBreedsImages(breedImages: BreedImage)=withContext(Dispatchers.IO) {
        appDatabase.breedImageDao().insert(breedImages.toBreedImageEntity(1))
    }

    override suspend fun insertBreedsImagesList(breedImages: List<BreedImage>, page: Int) =withContext(Dispatchers.IO) {
        appDatabase.breedImageDao().insertAll(breedImages.toBreedEntityList(page))
    }

    override suspend fun getLastRemoteKey(): BreedRemoteKeyDbData? = withContext(Dispatchers.IO) {
        appDatabase.breedsRemoteKeyDao().getLastRemoteKey()
    }

    override suspend fun saveRemoteKey(key: BreedRemoteKeyDbData) = withContext(Dispatchers.IO) {
        appDatabase.breedsRemoteKeyDao().saveRemoteKey(key)
    }

    override suspend fun clearBreeds() = withContext(Dispatchers.IO) {
        appDatabase.breedImageDao().deleteAll()
    }
    override suspend fun clearOutdatedBreeds(threshhold:Long) = withContext(Dispatchers.IO) {
        appDatabase.breedImageDao().deleteOutdatedBreeds(threshhold)
    }
    override suspend fun clearRemoteKeys() = withContext(Dispatchers.IO) {
        appDatabase.breedsRemoteKeyDao().clearRemoteKeys()
    }


}