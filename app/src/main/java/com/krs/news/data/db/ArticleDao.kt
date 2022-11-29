package com.krs.news.data.db

import androidx.room.*
import com.krs.news.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticle(article : Article): Long

    @Delete
    suspend fun deleteArticle(article : Article): Int

    @Query("SELECT * FROM articles")
    fun getArticles() : Flow<List<Article>>
}