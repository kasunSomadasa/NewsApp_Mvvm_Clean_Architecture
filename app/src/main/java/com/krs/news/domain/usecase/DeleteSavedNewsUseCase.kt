package com.krs.news.domain.usecase

import com.krs.news.data.model.Article
import com.krs.news.domain.repository.NewsRepository

class DeleteSavedNewsUseCase constructor(private val newsRepository: NewsRepository) {
    suspend fun execute(article: Article) : Int = newsRepository.deleteNews(article)
}