package com.example.hackchallengenewsfrontend.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.hackchallengenewsfrontend.networking.ArticleRepository
import com.example.hackchallengenewsfrontend.ui.screens.toHumanReadable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {
    private val _uiStateFlow = MutableStateFlow(ArticleUIState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    data class ArticleUIState(
        val title: String = "",
        val articleDescription: String = "",
        val articleText: String = "",
        val mainImage: String = "",
        val mainImageDescription: String? = null,
        val author: String = "",
        val newsSource: String = "",
        val date: String = "",
        val saved: Boolean = false
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
                saved = article.saved
            )
        }
    }
}


