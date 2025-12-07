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

    init {
        loadSavedArticles()
    }

    fun toggleFavorite(articleId: Int, isFavorited: Boolean) {
        // Optimistically update UI by removing the article
        val updatedArticles = _uiStateFlow.value.savedArticles.filter { it.id != articleId }
        _uiStateFlow.value = _uiStateFlow.value.copy(savedArticles = updatedArticles)

        // Make the API call
        viewModelScope.launch {
            val result = if (isFavorited) {
                articleRepository.unFavoriteArticle(articleId)
            } else {
                articleRepository.favoriteArticle(articleId)
            }

            // Reload saved articles on failure to restore correct state
            result.onFailure {
                loadSavedArticles()
            }
        }
    }


    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadSavedArticles() {
        viewModelScope.launch {
            articleRepository.getSavedArticles().onSuccess { articles ->
                _uiStateFlow.value = _uiStateFlow.value.copy(
                    savedArticles = articles
                )
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