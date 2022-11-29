package com.krs.news.domain.usecase

import androidx.paging.PagingData
import com.krs.news.data.model.Article
import com.krs.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSearchedNewsUseCase constructor(private val newsRepository: NewsRepository) {
    suspend fun execute(country : String, searchQuery : String, page : Int) : Flow<PagingData<Article>> {
        return newsRepository.getSearchNews(country, searchQuery, page)
    }
}