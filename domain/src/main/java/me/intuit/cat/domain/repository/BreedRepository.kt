package me.intuit.cat.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.intuit.cat.domain.model.BreedImage
import me.intuit.cat.domain.util.Result


interface BreedsRepository {
    
    fun getBreedsImage(): Flow<PagingData<BreedImage>>
    fun getBreedsImageDirectlyFromDB(): Flow<PagingData<BreedImage>>
    suspend fun getImagesDirectlyFromDB(): Result<List<BreedImage>>
    suspend fun sync(): Boolean
}
