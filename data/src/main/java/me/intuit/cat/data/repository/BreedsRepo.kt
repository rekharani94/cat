package me.intuit.cat.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.work.ListenableWorker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import me.intuit.cat.data.local.entity.toDomain
import me.intuit.cat.data.utils.NetworkHelper
import me.intuit.cat.domain.model.BreedImage
import me.intuit.cat.domain.repository.BreedsRepository
import me.intuit.cat.domain.util.Result
import me.intuit.cat.domain.util.getResult
import me.intuit.cat.domain.util.onError
import me.intuit.cat.domain.util.onSuccess
import java.io.IOException

class BreedsRepo(private val remote: BreedsDataSource.Remote,
                 private val local:BreedsDataSource.Local,
                 private val remoteMediator: BreedsRemoteMediator,
                 private val networkHelper: NetworkHelper): BreedsRepository {

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20
        private const val CACHE_TTL = 3*60* 60 * 1000 // Cache Time-To-Live (1 hour)


    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getBreedsImage(): Flow<PagingData<BreedImage>> = Pager(
        config = getDefaultPageConfig(),
        remoteMediator = remoteMediator,
        pagingSourceFactory = { local.breedsImages() }
    ).flow.map { pagingData ->
        pagingData.map { it.toDomain() }
    }.catch {
        throw it
    }.retryWhen { cause, attempt ->
        if (cause is java.io.IOException && attempt < 3) {
            kotlinx.coroutines.delay(2000)
            return@retryWhen true
        } else {
            return@retryWhen false
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getBreedsImageDirectlyFromDB(): Flow<PagingData<BreedImage>> = Pager(
        config = getDefaultPageConfig(),
        pagingSourceFactory = { local.breedsImages() }
    ).flow.map { pagingData ->
        pagingData.map { it.toDomain() }
    }.catch {
        throw it
    }.retryWhen { cause, attempt ->
        if (cause is IOException && attempt < 3) {
            delay(2000)
            return@retryWhen true
        } else {
            return@retryWhen false
        }
    }

    override suspend fun getImagesDirectlyFromDB(): Result<List<BreedImage>> {
        return local.getBreedImages().onSuccess {
            Result.Success(it)
        }.onError {
            Result.Error<Throwable>(it.fillInStackTrace())
        }
    }

    override suspend fun sync(): Boolean {
        try {
            if (networkHelper.isNetworkConnected()) {
                var currentPage = 1

                var hasMorePages = true

                while (hasMorePages) {
                    val data = remote.getBreedsImages(currentPage, 20)
                    val outdatedThreshold = System.currentTimeMillis() - CACHE_TTL

                    local.clearOutdatedBreeds(threshhold = outdatedThreshold)
                    data.getResult({

                        hasMorePages = if (it.data.isNotEmpty()) true else false
                        local.insertBreedsImagesList(it.data, currentPage)
                        currentPage++
                    }) {
                        hasMorePages=false
                    }
                }
                return true

            } else {
                return false
            }
        } catch (e: Exception) {
            return false
        }
    }

    fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = BreedsRepo.DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }
}

