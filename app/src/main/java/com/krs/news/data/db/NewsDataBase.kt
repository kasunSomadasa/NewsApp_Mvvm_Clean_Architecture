package com.krs.news.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.krs.news.data.model.Article

@Database(entities = [Article::class],
    version = 1,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class NewsDataBase : RoomDatabase(){
    abstract fun getArticleDao() : ArticleDao
}