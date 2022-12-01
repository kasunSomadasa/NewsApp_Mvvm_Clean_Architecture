package com.krs.news.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.krs.news.data.model.Article
import com.krs.news.data.model.Source
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: ArticleDao
    private lateinit var database: NewsDataBase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NewsDataBase::class.java
        ).build()
        dao = database.getArticleDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    /**
     * checking if the function save news correctly
     * checking if the function return saved list
     */
    @Test
    fun saveArticleTest() = runBlocking {
        val article = Article(
            1,
            "author1",
            "content1",
            "descrition1",
            "2022/12/10",
            Source("name1", "name1"),
            "title1",
            "url1",
            "image_url1"
        )

        dao.saveArticle(article)
        val savedArticle = dao.getArticles().first()
        Truth.assertThat(savedArticle[0]).isEqualTo(article)
    }


    /**
     * checking if the function delete news correctly
     * checking if the function return saved list
     */
    @Test
    fun deleteArticleTest() = runBlocking {
        val article = Article(
            1,
            "author1",
            "content1",
            "descrition1",
            "2022/12/10",
            Source("name1", "name1"),
            "title1",
            "url1",
            "image_url1"
        )

        dao.saveArticle(article)
        dao.deleteArticle(article)
        val articleList = dao.getArticles().first()
        Truth.assertThat(articleList).doesNotContain(article)
    }
}