package com.krs.news.data.api

import com.krs.news.BuildConfig
import com.krs.news.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country")
        country: String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY,
        ): NewsResponse


    @GET("v2/top-headlines")
    suspend fun getSearchedNews(
        @Query("country")
        country: String,
        @Query("q")
        searchQuery: String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY,
    ): NewsResponse
}