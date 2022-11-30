package com.krs.news.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.krs.news.data.api.NewsApiService
import com.krs.news.data.model.Article

class NewsPagingDataSource(
    private val newsApiService: NewsApiService,
    private val country: String,
    private val searchQuery: String,
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = if (searchQuery.isEmpty()) newsApiService.getNews(
                country,
                page
            ) else newsApiService.getSearchedNews(country, searchQuery, page)
            val repos = response.articles
            return LoadResult.Page(
                data = repos,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1),
                nextKey = if (repos.isEmpty()) null else page.plus(1)
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

}