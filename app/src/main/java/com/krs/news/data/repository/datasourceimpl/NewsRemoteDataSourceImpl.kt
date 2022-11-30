package com.krs.newsapp.data.repository.datasourceimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.krs.news.data.api.NewsApiService
import com.krs.news.data.model.Article
import com.krs.news.data.repository.datasource.NewsRemoteDataSource
import com.krs.news.data.repository.paging.NewsPagingDataSource
import kotlinx.coroutines.flow.Flow

class NewsRemoteDataSourceImpl constructor(
    private val newsApiService: NewsApiService
) : NewsRemoteDataSource {

    override suspend fun getNews(
        country: String,
        page: Int)
    : Flow<PagingData<Article>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            NewsPagingDataSource(
                newsApiService,
                country,
                ""
            )
        }
    ).flow

    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Flow<PagingData<Article>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            NewsPagingDataSource(
                newsApiService,
                country,
                searchQuery
            )
        }
    ).flow


}