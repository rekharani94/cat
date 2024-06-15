package me.intuit.cat.domain.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.intuit.cat.domain.repository.BreedsRepository
import me.intuit.cat.domain.usecase.BreedDetailUseCases
import me.intuit.cat.domain.usecase.GetBreedImagesFromDBUseCases
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
        catBreedsRepository: BreedsRepository,
    ): GetBreedsFromDBUseCases {
        return GetBreedsFromDBUseCases(catBreedsRepository)
    }


    @Provides
    @Singleton
    fun provideGetBreedsImagesFROMDBUseCases(
        catBreedsRepository: BreedsRepository,
    ): GetBreedImagesFromDBUseCases {
        return GetBreedImagesFromDBUseCases(catBreedsRepository)
    }


    @Provides
    @Singleton
    fun provideGetBreedsListUseCases(
        catBreedsRepository: BreedsRepository,
    ): BreedDetailUseCases {
        return BreedDetailUseCases(catBreedsRepository)
    }

}