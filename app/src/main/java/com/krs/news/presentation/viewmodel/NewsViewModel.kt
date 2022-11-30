package com.krs.news.presentation.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.krs.news.data.model.Article
import com.krs.news.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsViewModel constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
) : ViewModel() {

    // live data for news list
    private val _newsMutableLiveData: MutableLiveData<PagingData<Article>> = MutableLiveData()
    val newsLiveData: LiveData<PagingData<Article>>
        get() = _newsMutableLiveData

    // request news list from api and post it to the live data
    fun getNews(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val result = getNewsUseCase.execute(country, page).cachedIn(viewModelScope)
            result.collectLatest {
                _newsMutableLiveData.postValue(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // live data for searched news list
    private val _searchNewsMutableLiveData: MutableLiveData<PagingData<Article>> = MutableLiveData()
    val searchNewsLiveData: LiveData<PagingData<Article>>
        get() = _searchNewsMutableLiveData

    // request searched news list from api and post it to the live data
    fun getSearchedNews(country: String, searchQuery: String, page: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getSearchedNewsUseCase.execute(country, searchQuery, page)
                    .cachedIn(viewModelScope)
                result.collectLatest {
                    _searchNewsMutableLiveData.postValue(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    // live data to notify saved news index
    private val _savedNewsMutableLiveData: MutableLiveData<Long> = MutableLiveData()
    val savedNewsLiveData: LiveData<Long>
        get() = _savedNewsMutableLiveData

    // save news in local db
    fun saveNews(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        val rowId = saveNewsUseCase.execute(article)
        _savedNewsMutableLiveData.postValue(rowId)
    }

    // get saved news list as live data
    fun getSavedNews() = liveData {
        getSavedNewsUseCase.execute().collect {
            emit(it)
        }
    }

    // live data to notify deleted news index
    private val _deletedNewsMutableLiveData: MutableLiveData<Int> = MutableLiveData()
    val deletedNewsLiveData: LiveData<Int>
        get() = _deletedNewsMutableLiveData

    // delete news in local db
    fun deleteNews(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        val rowId = deleteSavedNewsUseCase.execute(article)
        _deletedNewsMutableLiveData.postValue(rowId)
    }
}