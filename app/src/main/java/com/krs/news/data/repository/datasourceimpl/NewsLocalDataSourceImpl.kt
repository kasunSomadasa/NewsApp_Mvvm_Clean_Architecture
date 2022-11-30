package com.krs.news.data.repository.datasourceimpl

import com.krs.news.data.db.ArticleDao
import com.krs.news.data.model.Article
import com.krs.news.data.repository.datasource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl constructor(private val articleDao: ArticleDao) :
    NewsLocalDataSource {

    override suspend fun saveArticleToDB(article: Article) : Long{
        return articleDao.saveArticle(article)
    }

    override suspend fun deleteArticle(article: Article) : Int{
        return articleDao.deleteArticle(article)
    }

    override fun getArticles(): Flow<List<Article>> {
        return articleDao.getArticles()
    }
}