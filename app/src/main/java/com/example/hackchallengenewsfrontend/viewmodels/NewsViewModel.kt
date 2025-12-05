package com.example.hackchallengenewsfrontend.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackchallengenewsfrontend.networking.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {
    private val _uiStateFlow = MutableStateFlow(NewsUIState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    data class NewsUIState(
        val feed: List<News> = emptyList(),
        val filters: List<String> = emptyList<String>(),
    ) {
        val filteredFeed: List<News>
            get() = feed
        // Use the below if we implement tagging
//            get() = if (filters.isEmpty()) feed else feed.filter { article ->
//                for(tag in article.tags) {
//                    return@filter filters.contains(tag)
//                }
//                return@filter false
//            }
    }

    init {
        viewModelScope.launch {
            articleRepository.getAllArticles()
                .onSuccess { feed ->
                    _uiStateFlow.value = _uiStateFlow.value.copy(
                        feed = feed
                    )
                }.onFailure {
                    _uiStateFlow.value = _uiStateFlow.value.copy(
                        feed = listOf(News(
                            title = "Winter storm snarls flights for post-Thanksgiving travelers in Chicago",
                            thumbnailUrl = "https://d3i6fh83elv35t.cloudfront.net/static/2025/11/GettyImages-2248617554-1200x800.jpg",
                            author = "Hello",
                            thumbnailDescription = "Winter Storm Snarls Air Travel In Chicago",
                            newsSource = "Me",
                            articleUrl = "no",
                            tags = emptyList(),
                            date = LocalDateTime.parse("2024-11-26T17:11:56"),
                        ))
                    )
                }
        }
    }
}


