package me.intuit.cat.domain.usecase

import kotlinx.coroutines.flow.Flow
import me.intuit.cat.domain.model.Article
import me.intuit.cat.domain.repository.TopHeadlineRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTopHeadlineUseCase @Inject constructor(private val topHeadlineRepository: TopHeadlineRepository) {

    operator fun invoke(country: String): Flow<List<Article>> {
        return topHeadlineRepository.getTopHeadlines(country)
    }

}