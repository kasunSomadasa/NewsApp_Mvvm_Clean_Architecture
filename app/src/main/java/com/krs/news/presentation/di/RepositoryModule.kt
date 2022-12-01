package com.krs.news.presentation.di

import com.krs.news.data.repository.NewsRepositoryImpl
import com.krs.news.data.repository.datasource.NewsLocalDataSource
import com.krs.news.data.repository.datasource.NewsRemoteDataSource
import com.krs.news.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        newsLocalDataSource: NewsLocalDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource, newsLocalDataSource)
    }
}