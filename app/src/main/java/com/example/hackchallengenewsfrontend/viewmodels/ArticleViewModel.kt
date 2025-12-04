package com.example.hackchallengenewsfrontend.viewmodels

import androidx.lifecycle.ViewModel
import com.example.hackchallengenewsfrontend.networking.ArticleRepository
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
        val date: String = ""
    )

}


