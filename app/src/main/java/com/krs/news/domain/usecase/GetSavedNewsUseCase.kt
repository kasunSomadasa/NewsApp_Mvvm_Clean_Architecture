package com.krs.news.domain.usecase

import com.krs.news.data.model.Article
import com.krs.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase constructor(private val newsRepository: NewsRepository) {
    fun execute() : Flow<List<Article>> {
        return newsRepository.getNewsArticlesFromDb()
    }
}