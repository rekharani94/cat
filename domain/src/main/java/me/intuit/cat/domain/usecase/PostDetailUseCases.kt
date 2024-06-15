package me.intuit.cat.domain.usecase

data class PostDetailUseCases(
    val getPostUseCase: GetPostUseCase,
    val getCommentUseCase: GetCommentUseCase
)
