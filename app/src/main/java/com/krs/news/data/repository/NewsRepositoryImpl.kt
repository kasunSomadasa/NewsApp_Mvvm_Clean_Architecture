package com.krs.news.data.repository

import androidx.paging.PagingData
import com.krs.news.data.model.Article
import com.krs.news.data.repository.datasource.NewsLocalDataSource
import com.krs.news.data.repository.datasource.NewsRemoteDataSource
import com.krs.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImpl constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
) : NewsRepository {

    /**
     * return news response as PagingData
     */
    override suspend fun getNews(country: String, page: Int): Flow<PagingData<Article>> {
        return newsRemoteDataSource.getNews(country, page)
    }

    /**
     * return searched news response as PagingData
     */
    override suspend fun getSearchNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Flow<PagingData<Article>> {
        return newsRemoteDataSource.getSearchedNews(country, searchQuery, page)
    }

    /**
     * return saved news index
     */
    override suspend fun saveNews(article: Article): Long {
        return newsLocalDataSource.saveArticleToDB(article)
    }

    /**
     * return deleted news index
     */
    override suspend fun deleteNews(article: Article): Int {
        return newsLocalDataSource.deleteArticle(article)
    }

    /**
     * return saved news list as flow
     */
    override fun getNewsArticlesFromDb(): Flow<List<Article>> {
        return newsLocalDataSource.getArticles()
    }
}