package com.krs.news.presentation.di

import android.app.Application
import androidx.room.Room
import com.krs.news.data.db.ArticleDao
import com.krs.news.data.db.NewsDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideNewsDatabase(app: Application): NewsDataBase {
        return Room.databaseBuilder(app, NewsDataBase::class.java, "news_db")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Singleton
    @Provides
    fun provideArticleDao(newsDataBase: NewsDataBase): ArticleDao {
        return newsDataBase.getArticleDao()
    }
}