package com.krs.news.data.repository.datasource

import androidx.paging.PagingData
import com.krs.news.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRemoteDataSource {
    suspend fun getNews(country : String, page : Int): Flow<PagingData<Article>>
    suspend fun getSearchedNews(country : String, searchQuery: String, page : Int): Flow<PagingData<Article>>
}