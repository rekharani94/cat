package me.intuit.cat.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.intuit.cat.data.local.entity.mapToDomain
import me.intuit.cat.data.api.NetworkService
import me.intuit.cat.data.repository.BreedsRepo.Companion.DEFAULT_PAGE_INDEX
import me.intuit.cat.domain.model.BreedImage

import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1
@ExperimentalPagingApi
 class BreedPagingSource @Inject constructor(private val apiService: NetworkService) : PagingSource<Int, BreedImage>() {

    /*override fun getRefreshKey(state: PagingState<Int, Breed>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
*/

   /* override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Breed> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return remoteDataSource.getBreeds(page, params.loadSize).getResult({
            LoadResult.Page(
                data = it.data,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (it.data.isEmpty()) null else page + 1
            )
        }, {
            LoadResult.Error(it.error)
        })

    }*/

    /*override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Breed> {
        //for first case it will be null, then we can pass some default value, in our case it's 1
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = apiService.breeds(page, params.loadSize)
           val result =  response.mapToDomain()
              //getDoggoImages(page, params.loadSize)
            LoadResult.Page(
                result, prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }*/

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BreedImage> {
        //for first case it will be null, then we can pass some default value, in our case it's 1
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = apiService.getBreedImagesList(page, params.loadSize)
            val result =  response.mapToDomain()
            LoadResult.Page(
                result, prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, BreedImage>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}