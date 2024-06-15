package me.intuit.cat.data.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.intuit.cat.data.api.NetworkService
import me.intuit.cat.data.repository.TopHeadlineRepositoryImpl
import me.intuit.cat.domain.repository.TopHeadlineRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideTopHeadlineRepository(networkService: NetworkService): TopHeadlineRepository {
        return TopHeadlineRepositoryImpl(networkService)
    }

}