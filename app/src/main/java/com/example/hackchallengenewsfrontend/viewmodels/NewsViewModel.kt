package com.example.hackchallengenewsfrontend.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor() : ViewModel() {
    private val _uiStateFlow = MutableStateFlow(NewsUIState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    data class NewsUIState(
        val feed: List<News> = emptyList<News>(),
        val filters: List<NewsTags> = emptyList<NewsTags>(),
    ) {
        val filteredFeed: List<News>
            get() = feed.filter { article ->
                for(tag in article.tags) {
                    return@filter filters.contains(tag)
                }
                return@filter false
            }
    }
}


