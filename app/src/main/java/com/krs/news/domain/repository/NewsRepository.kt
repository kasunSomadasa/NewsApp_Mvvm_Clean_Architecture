package com.krs.news.domain.repository

import androidx.paging.PagingData
import com.krs.news.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNews(country: String, page: Int): Flow<PagingData<Article>>
    suspend fun getSearchNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Flow<PagingData<Article>>

    suspend fun saveNews(article: Article): Long
    suspend fun deleteNews(article: Article): Int
    fun getNewsArticlesFromDb(): Flow<List<Article>>
}