package me.intuit.cat.domain.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.intuit.cat.domain.usecase.GetCommentUseCase
import me.intuit.cat.domain.usecase.GetPostUseCase
import me.intuit.cat.domain.usecase.PostDetailUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetPostUseCase(): GetPostUseCase {
        return GetPostUseCase()
    }

    @Provides
    @Singleton
    fun provideGetCommentUseCase(): GetCommentUseCase {
        return GetCommentUseCase()
    }

    @Provides
    @Singleton
    fun providePostDetailUseCases(
        getPostUseCase: GetPostUseCase,
        getCommentUseCase: GetCommentUseCase
    ): PostDetailUseCases {
        return PostDetailUseCases(getPostUseCase, getCommentUseCase)
    }

}