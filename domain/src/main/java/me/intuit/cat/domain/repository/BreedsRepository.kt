package me.intuit.cat.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.intuit.cat.domain.model.Breed
import me.intuit.cat.domain.model.BreedImage

interface BreedsRepository {
    fun getBreedsImagesList(): Flow<PagingData<BreedImage>>
    suspend fun getBreedsDirectlyFromDB(): Flow<PagingData<BreedImage>>
   suspend fun getBreedsfromDB(): Flow<List<Breed>>
    fun getBreeds(imageId: String): Flow<List<Breed>>
   // suspend fun sync(): Boolean
}