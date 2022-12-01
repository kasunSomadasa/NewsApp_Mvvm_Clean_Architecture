package com.krs.news.data.api

import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NewsApiServiceTest {
    private lateinit var newsApiService: NewsApiService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        newsApiService = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)

    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    private fun enqueueMockResponse(
        fileName: String
    ) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    /**
     * checking if the service function send the request to the server properly
     */
    @Test
    fun getNews_requestSent_receivedExpected() {
        runBlocking {
            enqueueMockResponse("news_response.json")
            val responseBody = newsApiService.getNews("us", 1)
            val request = server.takeRequest()
            Truth.assertThat(responseBody).isNotNull()
            Truth.assertThat(request.path)
                .isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=92055433ed86460e91b6522249218292")
        }
    }

    /**
     * checking if our service function receives the response properly
     */
    @Test
    fun getNews_receivedResponse_correctPageItemSize() {
        runBlocking {
            enqueueMockResponse("news_response.json")
            val responseBody = newsApiService.getNews("us", 1)
            val newsArticlesList = responseBody!!.articles
            Truth.assertThat(newsArticlesList.size).isEqualTo(20)
        }
    }

    /**
     * checking the content of the received objects
     */
    @Test
    fun getNews_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("news_response.json")
            val responseBody = newsApiService.getNews("us", 1)
            val newsArticlesList = responseBody!!.articles
            val newsArticle = newsArticlesList[0]
            Truth.assertThat(newsArticle.author).isEqualTo("WSBTV.com News Staff")
            Truth.assertThat(newsArticle.url)
                .isEqualTo("https://www.wsbtv.com/news/local/cobb-county/2-cobb-county-sheriffs-deputies-shot-officials-confirm/AONLJLYGP5HENOEITPI7WYAOTQ/")
            Truth.assertThat(newsArticle.publishedAt).isEqualTo("2022-09-09T08:15:35Z")
        }
    }
}