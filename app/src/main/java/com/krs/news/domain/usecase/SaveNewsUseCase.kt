package com.krs.news.domain.usecase

import com.krs.news.data.model.Article
import com.krs.news.domain.repository.NewsRepository

class SaveNewsUseCase constructor(private val newsRepository: NewsRepository) {
    suspend fun execute(article: Article) : Long = newsRepository.saveNews(article)

}