package me.intuit.cat.data.repository

import androidx.paging.PagingSource

import me.intuit.cat.data.local.entity.BreedEntity
import me.intuit.cat.data.local.entity.BreedImageEntity
import me.intuit.cat.data.local.entity.BreedRemoteKeyDbData
import me.intuit.cat.domain.model.Breed
import me.intuit.cat.domain.model.BreedImage
import me.intuit.cat.domain.util.Result

interface BreedsDataSource {

    interface Remote {
        suspend fun getBreedsImages(page: Int, limit: Int): Result<List<BreedImage>>
    }

    interface Local {
        fun breedsImages(): PagingSource<Int, BreedImageEntity>
        suspend fun getBreedImages(): Result<List<BreedImage>>
        suspend fun insertBreedsImages(breedImages: BreedImage)
        suspend fun insertBreedsImagesList(breedImages: List<BreedImage>)
        suspend fun getLastRemoteKey(): BreedRemoteKeyDbData?
        suspend fun saveRemoteKey(key: BreedRemoteKeyDbData)
        suspend fun clearBreeds()
        suspend fun clearRemoteKeys()

    }
}