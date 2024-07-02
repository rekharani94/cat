package me.intuit.cat.domain.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.intuit.cat.domain.repository.BreedsRepository
import me.intuit.cat.domain.usecase.GetBreedsFromDBUseCases
import me.intuit.cat.domain.usecase.GetBreedsListUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {



    @Provides
    @Singleton
    fun provideDetailDetailUseCases(
        catBreedsRepository: BreedsRepository,
    ): GetBreedsListUseCase {
        return GetBreedsListUseCase(catBreedsRepository)
    }


    @Provides
    @Singleton
    fun provideGetBreedsFROMDBUseCases(
        repo: BreedsRepository,
    ): GetBreedsFromDBUseCases {
        return GetBreedsFromDBUseCases(repo)
    }

}