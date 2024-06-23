package me.intuit.cat.data.di.module

import androidx.paging.ExperimentalPagingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.intuit.cat.data.api.NetworkService
import me.intuit.cat.data.local.AppDatabase
import me.intuit.cat.data.repository.BreedsRemoteMediator

import me.intuit.cat.data.repository.BreedsDataSource
import me.intuit.cat.data.repository.BreedsRepo
import me.intuit.cat.data.repository.LocalDataSource
import me.intuit.cat.data.repository.RemoteDataSource
import me.intuit.cat.domain.repository.BreedsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {




    @Provides
    @Singleton
    fun provideBreedRemoteDataSource(apiService: NetworkService): BreedsDataSource.Remote {
        return RemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideMovieLocalDataSource(
        appDaDatabase: AppDatabase
    ): BreedsDataSource.Local {
        return LocalDataSource(appDaDatabase)
    }

    @Provides
    @Singleton
    fun provideMediator(
        breedLocalDataSource: BreedsDataSource.Local,
        breedRemoteDataSource: BreedsDataSource.Remote,
        appDatabase: AppDatabase
    ): BreedsRemoteMediator {
        return BreedsRemoteMediator( breedRemoteDataSource,appDatabase)
    }

    @Provides
    @Singleton
    fun provideRepositoryRepository(
        movieRemote: BreedsDataSource.Remote,
        movieLocal: BreedsDataSource.Local,
        movieRemoteMediator: BreedsRemoteMediator,
    ): BreedsRepository {
        return BreedsRepo(movieRemote, movieLocal, movieRemoteMediator)
    }


}