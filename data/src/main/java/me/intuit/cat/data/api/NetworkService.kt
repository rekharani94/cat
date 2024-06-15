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


    @GET(BREEDS)
    suspend   fun breeds( @Query("page") page:Int,@Query("limit") limit:Int = 10): List<BreedDto>


    @GET("images/search")
    suspend  fun getBreedImages(
        @Query("breed_id") breed_id: String,
        @Query("limit") limit: Int,
        @Query("size") size: String,
        @Query("mime_types") mime_types: String?
    ): List<BreedImageDto>

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


// @GET("images/{image_id}/breeds")
//https://api.thecatapi.com/v1/images/search?limit=10
    //https://api.thecatapi.com/v1/images/search?limit=10&page=2 - image list
//https://api.thecatapi.com/v1/images/0XYvRd7oD
    //https://api.thecatapi.com/v1/images/?limit=10&page=0&order=DESC
//https://api.thecatapi.com/v1/breeds
    //https://api.thecatapi.com/v1/images/?limit=10&page=0&order=DESC
    //https://api.thecatapi.com/v1/images/search?size=med&mime_types=jpg&format=json&has_breeds=true&order=RANDOM&page=0&limit=30
    ///https://api.thecatapi.com/v1/images/:i/breeds
   // https://api.thecatapi.com/v1/images/:image_id/breeds - bread
}

/*  @Headers("X-Api-Key: $API_KEY")
    @GET("images/")
    suspend fun getCatsImageList(
                            @Query("page") page: Int,
                            @Query("limit") limit: Int): ResponseDto<ArrayList<CatImageListItemDTO>>

    @Headers("X-Api-Key: $API_KEY")
    @GET("images")
    suspend fun getCatBreedDetail(
        @Query("image-id") imageId: String): ResponseDto<CatBreedDetailsDTO>*/
//@Query("limit") limit:Int, @Query("page") page:Int

/*    @GET(IMAGE)
    fun getImage(@Query(BREED_ID) breedId: String): List<BreedDetails>*/
