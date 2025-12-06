package com.example.hackchallengenewsfrontend.viewmodels

import android.media.browse.MediaBrowser
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import com.example.hackchallengenewsfrontend.networking.ArticleRepository
import com.example.hackchallengenewsfrontend.ui.screens.toHumanReadable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {
    private val _uiStateFlow = MutableStateFlow(ArticleUIState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    fun toggleFavorite(articleId: Int, isFavorited: Boolean) {
        // Optimistic UI update - respond immediately
        _uiStateFlow.value = _uiStateFlow.value.copy(
            saved = !isFavorited
        )

        // Then make the API call in the background
        viewModelScope.launch {
            val result = if (isFavorited) {
                articleRepository.unFavoriteArticle(articleId)
            } else {
                articleRepository.favoriteArticle(articleId)
            }

            // Revert on failure
            result.onFailure {
                _uiStateFlow.value = _uiStateFlow.value.copy(
                    saved = isFavorited // Revert to original state
                )
            }
        }
    }


    data class ArticleUIState(
        val title: String = "",
        val articleDescription: String = "",
        val articleText: String = "",
        val mainImage: String = "",
        val mainImageDescription: String? = null,
        val author: String = "",
        val newsSource: String = "",
        val date: String = "",
        val saved: Boolean = false,
        val id: Int = 0
    )

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun loadArticle(articleID: Int) {
        val currentArticle = articleRepository.getArticleByID(articleID)
        currentArticle.onSuccess { article ->
            _uiStateFlow.value = _uiStateFlow.value.copy(
                title = article.title,
                articleDescription = "",
                articleText = article.text,
                mainImage = article.thumbnailUrl ?: "",
                mainImageDescription = article.thumbnailDescription,
                author = article.author,
                newsSource = article.newsSource,
                date = article.date?.toHumanReadable() ?: "A New Era",
                saved = article.saved,
                id = article.id
            )
        }
    }

    suspend fun getAudio(): MediaItem? {
        articleRepository.getAudio(_uiStateFlow.value.id).onSuccess {
            return it
        }
        return null
    }
}


