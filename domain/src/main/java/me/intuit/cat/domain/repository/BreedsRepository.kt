package me.intuit.cat.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.intuit.cat.domain.model.Breed
import me.intuit.cat.domain.model.BreedImage

interface BreedsRepository {
    fun getBreedsImagesList(): Flow<PagingData<BreedImage>>
    suspend fun getBreedsDirectlyFromDB(): Flow<PagingData<BreedImage>>
     fun getBreedsImagesFromRemote(
        imageId: String,
        imageLimit: Int,
        size: String,
        memeTypes: String
    ): Flow<List<BreedImage>>
   suspend fun getBreedsImagesFromDB(): Flow<List<Breed>>
    fun getBreeds(imageId: String): Flow<List<Breed>>
   // suspend fun sync(): Boolean
}