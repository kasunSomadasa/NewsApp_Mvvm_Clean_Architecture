package com.krs.news.presentation.di

import com.krs.news.presentation.adapter.NewsAdapter
import com.krs.news.presentation.adapter.NewsSearchAdapter
import com.krs.news.presentation.adapter.SavedNewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AdapterModule {

    @Singleton
    @Provides
    fun provideNewsAdapter(): NewsAdapter {
        return NewsAdapter()
    }

    @Singleton
    @Provides
    fun provideSavedNewsAdapter(): SavedNewsAdapter {
        return SavedNewsAdapter()
    }

    @Singleton
    @Provides
    fun provideNewsSearchAdapter(): NewsSearchAdapter {
        return NewsSearchAdapter()
    }
}