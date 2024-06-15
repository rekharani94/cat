package me.intuit.cat.data.repository

import androidx.paging.PagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.intuit.cat.data.exception.DataNotAvailableException
import me.intuit.cat.data.local.AppDatabase
import me.intuit.cat.data.local.entity.BreedEntity
import me.intuit.cat.data.local.entity.BreedImageEntity
import me.intuit.cat.data.local.entity.BreedRemoteKeyDbData
import me.intuit.cat.data.local.entity.toDomain
import me.intuit.cat.data.mapper.toBreedEntityList
import me.intuit.cat.data.mapper.toBreedImageEntity
import me.intuit.cat.data.mapper.toEntityList
import me.intuit.cat.domain.model.Breed
import me.intuit.cat.domain.model.BreedImage
import me.intuit.cat.domain.util.Result
import javax.inject.Inject

class LocalDataSource  @Inject constructor (private val appDatabase: AppDatabase,

                                            ):BreedsDataSource.Local {
    override fun breeds(): PagingSource<Int, BreedEntity> {
        return appDatabase.breedDao().breeds()
    }

    override fun breedsImages(): PagingSource<Int, BreedImageEntity> {
        return  appDatabase.breedImageDao().getAllImages()
    }
    override suspend fun getBreeds(): Result<List<Breed>> =
        withContext(Dispatchers.IO){
            val breeds = appDatabase.breedDao().getAll()
            return@withContext if (breeds.isNotEmpty()) {
                Result.Success(breeds.map { it.toDomain() })
            } else {
                me.intuit.cat.domain.util.Result.Error(DataNotAvailableException())
            }
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
        appDatabase.breedImageDao().insert(breedImages.toBreedImageEntity())
    }

    override suspend fun insertBreedsImagesList(breedImages: List<BreedImage>) =withContext(Dispatchers.IO) {
        appDatabase.breedImageDao().insertAll(breedImages.toBreedEntityList())
    }

    override suspend fun insertBreed(breed: List<Breed>) =
        withContext(Dispatchers.IO) {
        appDatabase.breedDao().insertAll(breed.toEntityList())
    }


    override suspend fun getLastRemoteKey(): BreedRemoteKeyDbData? = withContext(Dispatchers.IO) {
        appDatabase.breedsRemoteKeyDao().getLastRemoteKey()
    }

    override suspend fun saveRemoteKey(key: BreedRemoteKeyDbData) = withContext(Dispatchers.IO) {
        appDatabase.breedsRemoteKeyDao().saveRemoteKey(key)
    }

    override suspend fun clearBreeds() = withContext(Dispatchers.IO) {
        appDatabase.breedDao().deleteAll()
    }

    override suspend fun clearRemoteKeys() = withContext(Dispatchers.IO) {
        appDatabase.breedsRemoteKeyDao().clearRemoteKeys()
    }


}