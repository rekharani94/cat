package me.intuit.cat.data.repository

import me.intuit.cat.data.api.NetworkService
import me.intuit.cat.data.local.entity.toDomain
import me.intuit.cat.domain.model.BreedImage
import me.intuit.cat.domain.util.Result

class RemoteDataSource(private val apiService: NetworkService):BreedsDataSource.Remote {
    override suspend fun getBreedsImages(page: Int, limit: Int): Result<List<BreedImage>> = try {
        val result = apiService.getBreedImagesList(page,limit)
        Result.Success(result.map { it.toDomain() })
    } catch (e: Exception) {
        Result.Error(e)
    }


    override suspend fun getBreedImages(breedId:String,limit:Int,size:String,mime_types:String): Result<List<BreedImage>> = try {
        val result = apiService.getBreedImages(breedId,limit,size,mime_types)
        Result.Success(result.map { it.toDomain() })
    } catch (e: Exception) {
        Result.Error(e)
    }
}