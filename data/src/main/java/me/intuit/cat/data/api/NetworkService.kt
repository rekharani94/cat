package me.intuit.cat.data.api

import me.intuit.cat.data.local.entity.BreedDto
import me.intuit.cat.data.local.entity.BreedImageDto
import me.intuit.cat.data.utils.AppConstant.BREEDS
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @GET("images/{image_id}/breeds")
    suspend  fun getBreedImages(
       @Path("image_id") imageId:String
    ): List<BreedDto>
    @GET("images/search")
    suspend  fun getBreedImagesList(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("has_breeds") hasBreeds:Boolean = true,
    ): List<BreedImageDto>

}
