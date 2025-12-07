package com.example.hackchallengenewsfrontend.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackchallengenewsfrontend.networking.ArticleRepository
import com.example.hackchallengenewsfrontend.networking.Outlet
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

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _allArticles = MutableStateFlow<List<News>>(emptyList())

    // NEW: Add outlet filtering state
    private val _selectedOutletId = MutableStateFlow<Int?>(null)
    val selectedOutletId = _selectedOutletId.asStateFlow()

    private val _availableOutlets = MutableStateFlow<List<Outlet>>(emptyList())
    val availableOutlets = _availableOutlets.asStateFlow()

    init {
        viewModelScope.launch {
            val loginState = articleRepository.loginInfo
            if (!loginState.value.isLoggedIn) {
                articleRepository.login("janedoe", "1234")
            }

            // Load outlets
            articleRepository.getAllOutlets().onSuccess { outlets ->
                _availableOutlets.value = outlets
            }

            // Load articles
            articleRepository.getKNewestArticles(100)
                .onSuccess { feed ->
                    _allArticles.value = feed
                    filterArticles()
                }.onFailure {
                    _uiStateFlow.value = _uiStateFlow.value.copy(
                        feed = listOf(
                            News(
                                title = "Error occured in the app",
                                thumbnailUrl = "https://d3i6fh83elv35t.cloudfront.net/static/2025/11/GettyImages-2248617554-1200x800.jpg",
                                author = "Shadman, Ryan, Boris, Colin, Ethan",
                                thumbnailDescription = "Winter Storm Snarls Air Travel In Chicago",
                                newsSource = "This App",
                                articleUrl = "no",
                                tags = emptyList(),
                                date = LocalDateTime.parse("2024-11-26T17:11:56"),
                                id = -1,
                                text = "Report the error if you can trust (no one will see this)"
                            )
                        )
                    )
                }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        filterArticles()
    }

    // NEW: Add outlet selection method
    fun selectOutlet(outletId: Int?) {
        _selectedOutletId.value = outletId
        _searchQuery.value = "" // Clear search when filtering by outlet

        viewModelScope.launch {
            if (outletId == null) {
                // Show all articles
                articleRepository.getKNewestArticles(100).onSuccess { articles ->
                    _allArticles.value = articles
                    filterArticles()
                }
            } else {
                // Show articles from selected outlet
                articleRepository.getTopArticlesByOutlet(outletId, 100).onSuccess { articles ->
                    _allArticles.value = articles
                    filterArticles()
                }
            }
        }
    }

    private fun filterArticles() {
        val query = _searchQuery.value.lowercase()
        val filtered = if (query.isEmpty()) {
            _allArticles.value
        } else {
            _allArticles.value.filter { article ->
                article.title.lowercase().contains(query) ||
                        article.author.lowercase().contains(query) ||
                        article.newsSource.lowercase().contains(query)
            }
        }
        _uiStateFlow.value = _uiStateFlow.value.copy(feed = filtered)
    }

    fun toggleFavorite(articleId: Int, isFavorited: Boolean) {
        _uiStateFlow.value = _uiStateFlow.value.copy(
            feed = _uiStateFlow.value.feed.map { article ->
                if (article.id == articleId) {
                    article.copy(saved = !isFavorited)
                } else {
                    article
                }
            }
        )

        viewModelScope.launch {
            if (isFavorited) {
                articleRepository.unFavoriteArticle(articleId)
            } else {
                articleRepository.favoriteArticle(articleId)
            }
        }
    }

    data class NewsUIState(
        val feed: List<News> = emptyList(),
        val filters: List<String> = emptyList<String>()
    ) {
        val filteredFeed: List<News>
            get() = feed
    }
}
