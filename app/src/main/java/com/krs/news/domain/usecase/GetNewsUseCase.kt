package com.krs.news.domain.usecase

import androidx.paging.PagingData
import com.krs.news.data.model.Article
import com.krs.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNewsUseCase constructor(private val newsRepository: NewsRepository) {

    // also we can do some business logic here. get data and modify into another format
    suspend fun execute(country : String, page : Int) : Flow<PagingData<Article>> {
        return newsRepository.getNews(country, page)
    }
}