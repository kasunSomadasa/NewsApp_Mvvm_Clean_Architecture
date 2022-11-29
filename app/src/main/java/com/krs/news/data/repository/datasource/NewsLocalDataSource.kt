package com.krs.news.data.repository.datasource

import com.krs.news.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    suspend fun saveArticleToDB(article: Article): Long
    suspend fun deleteArticle(article : Article): Int
    fun getArticles() : Flow<List<Article>>
}