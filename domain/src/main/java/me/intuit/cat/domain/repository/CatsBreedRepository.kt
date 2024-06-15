package me.intuit.cat.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.intuit.cat.domain.model.Breed
import me.intuit.cat.domain.model.BreedImage
import me.intuit.cat.domain.util.Result


interface CatsBreedsRepository {


    fun getBreeds(limit: Int, page: Int): Flow<PagingData<Breed>>
    fun getBreedsDirectlyFromDB(): Flow<PagingData<Breed>>
    fun getBreedsImages(
        breedId: String,
        imageLimit: Int,
        size: String,
        memeTypes: String?
    ): Flow<PagingData<BreedImage>>

    suspend fun getImagesDirectlyFromDB(): Result<List<BreedImage>>
    suspend fun sync(): Boolean
}
