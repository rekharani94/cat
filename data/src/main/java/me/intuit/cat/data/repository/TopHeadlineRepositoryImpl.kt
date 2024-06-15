package me.intuit.cat.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import me.intuit.cat.data.api.NetworkService
import me.intuit.cat.domain.model.Article
import me.intuit.cat.domain.repository.TopHeadlineRepository
import javax.inject.Inject

class TopHeadlineRepositoryImpl @Inject constructor(private val networkService: NetworkService) :
    TopHeadlineRepository {

    override fun getTopHeadlines(country: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getTopHeadlines(country))
        }.map {
            it.articles
        }
    }

}