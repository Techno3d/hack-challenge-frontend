package com.example.hackchallengenewsfrontend.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackchallengenewsfrontend.networking.ArticleRepository
import com.example.hackchallengenewsfrontend.viewmodels.NewsViewModel.NewsUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {
    private val _uiStateFlow = MutableStateFlow(SavedUIState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _allSavedArticles = MutableStateFlow<List<News>>(emptyList())

    init {
        loadSavedArticles()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        filterArticles()
    }

    private fun filterArticles() {
        val query = _searchQuery.value.lowercase()
        val filtered = if (query.isEmpty()) {
            _allSavedArticles.value
        } else {
            _allSavedArticles.value.filter { article ->
                article.title.lowercase().contains(query) ||
                        article.author.lowercase().contains(query) ||
                        article.newsSource.lowercase().contains(query)
            }
        }
        _uiStateFlow.value = _uiStateFlow.value.copy(savedArticles = filtered)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadSavedArticles() {
        viewModelScope.launch {
            articleRepository.getSavedArticles().onSuccess { articles ->
                _allSavedArticles.value = articles
                filterArticles()
            }
        }
    }

    data class SavedUIState(
        val savedArticles: List<News> = emptyList<News>()
    )

    init {
        viewModelScope.launch {
            val loginState = articleRepository.loginInfo
            if(!loginState.value.isLoggedIn) {
                articleRepository.login("johndoe", "1234")
            }
            articleRepository.getSavedArticles().onSuccess {
                _uiStateFlow.value = _uiStateFlow.value.copy(
                    savedArticles = it
                )
            }
        }
    }
}