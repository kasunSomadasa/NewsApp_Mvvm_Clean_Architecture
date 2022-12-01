package com.krs.news.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.krs.news.data.model.Article
import com.krs.news.data.model.Source
import com.krs.news.data.repository.FakeNewsRepository
import com.krs.news.domain.usecase.*
import com.krs.newsapp.data.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule =
        InstantTaskExecutorRule()

    private lateinit var newsViewModel: NewsViewModel


    @Before
    fun setUp() {
        val fakeNewsRepository = FakeNewsRepository()
        val getNewsUseCase = GetNewsUseCase(fakeNewsRepository)
        val getSearchedNewsUseCase = GetSearchedNewsUseCase(fakeNewsRepository)
        val saveNewsUseCase = SaveNewsUseCase(fakeNewsRepository)
        val getSavedNewsUseCase = GetSavedNewsUseCase(fakeNewsRepository)
        val deleteSavedNewsUseCase = DeleteSavedNewsUseCase(fakeNewsRepository)
        newsViewModel = NewsViewModel(
            getNewsUseCase,
            getSearchedNewsUseCase,
            saveNewsUseCase,
            getSavedNewsUseCase,
            deleteSavedNewsUseCase
        )
    }

    @After
    fun tearDown() {
    }

    /**
     * checking if the function save news correctly
     */
    @Test
    fun saveNews() = runBlocking {
        val article = Article(
            1,
            "author3",
            "content3",
            "descrition3",
            "2022/12/10",
            Source("name3", "name3"),
            "title3",
            "url3",
            "image_url3"
        )

        newsViewModel.saveNews(article)
        val insertedItemId = newsViewModel.savedNewsLiveData.getOrAwaitValue()
        Truth.assertThat(insertedItemId).isGreaterThan(0)
    }

    /**
     * checking if the function delete news correctly
     */
    @Test
    fun deleteNews() = runBlocking {
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

        newsViewModel.deleteNews(article)
        val deletedItemId = newsViewModel.deletedNewsLiveData.getOrAwaitValue()
        Truth.assertThat(deletedItemId).isGreaterThan(0)
    }

    /**
     * checking if the function return saved list
     */
    @Test
    fun getSavedNews_returnCurrentSavedList() = runBlocking {
        val articleList = mutableListOf<Article>()
        articleList.add(
            Article(
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
        )
        articleList.add(
            Article(
                2,
                "author2",
                "content2",
                "descrition2",
                "2022/12/20",
                Source("name2", "name2"),
                "title2",
                "url2",
                "image_url2"
            )
        )

        val savedArticleList = newsViewModel.getSavedNews().getOrAwaitValue()
        Truth.assertThat(savedArticleList).isEqualTo(articleList)
    }
}