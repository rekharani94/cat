package me.intuit.cat.domain.repository

import kotlinx.coroutines.flow.Flow
import me.intuit.cat.domain.model.Article

interface TopHeadlineRepository {

    fun getTopHeadlines(country: String): Flow<List<Article>>

}