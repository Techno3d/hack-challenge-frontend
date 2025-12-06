package com.example.hackchallengenewsfrontend.viewmodels

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