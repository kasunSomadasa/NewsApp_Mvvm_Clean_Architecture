package com.krs.news.data.repository

import androidx.paging.PagingData
import com.krs.news.data.model.Article
import com.krs.news.domain.repository.NewsRepository
import com.krs.news.data.model.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeNewsRepository : NewsRepository {

    private val articleList = mutableListOf<Article>()
    private val searchedArticleList = mutableListOf<Article>()

    init {
        articleList.add(
            Article(
                1,
                "author1",
                "content1",
                "descrition1",
                "2022/12/10",
                Source("name1", "name1"),
                "title1",
                "url1",
                "image_url1"
            )
        )
        articleList.add(
            Article(
                2,
                "author2",
                "content2",
                "descrition2",
                "2022/12/20",
                Source("name2", "name2"),
                "title2",
                "url2",
                "image_url2"
            )
        )

        searchedArticleList.add(
            Article(
                1,
                "author3",
                "content3",
                "descrition3",
                "2022/12/10",
                Source("name3", "name3"),
                "title3",
                "url3",
                "image_url3"
            )
        )
        searchedArticleList.add(
            Article(
                2,
                "author4",
                "content4",
                "descrition4",
                "2022/12/20",
                Source("name4", "name4"),
                "title4",
                "url4",
                "image_url4"
            )
        )

    }

    override suspend fun getNews(country: String, page: Int): Flow<PagingData<Article>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSearchNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Flow<PagingData<Article>> {
        TODO("Not yet implemented")
    }


    override suspend fun saveNews(article: Article): Long {
        var article = Article(
            3,
            "author3",
            "content3",
            "descrition3",
            "2022/12/10",
            Source("name3", "name3"),
            "title3",
            "url3",
            "image_url3"
        )

        articleList.add(article)
        return articleList.size.toLong()
    }

    override suspend fun deleteNews(article: Article): Int {
        var article = Article(
            1,
            "author1",
            "content1",
            "descrition1",
            "2022/12/10",
            Source("name1", "name1"),
            "title1",
            "url1",
            "image_url1"
        )

        articleList.remove(article)
        return 1
    }

    override fun getNewsArticlesFromDb(): Flow<List<Article>> {
        return flowOf(articleList)
    }
}