package com.example.hackchallengenewsfrontend.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hackchallengenewsfrontend.components.NewsCard
import com.example.hackchallengenewsfrontend.viewmodels.NewsViewModel

@Composable
fun NewsScreen(newsViewModel: NewsViewModel) {
    val uiState by newsViewModel.uiStateFlow.collectAsStateWithLifecycle()

    Text("This is the NewsScreen")
    LazyColumn {
        // Header
        item {
            Text("(Name of App)", fontSize = 25.sp)
        }
        // Body
        items(uiState.filteredFeed) { article ->
            NewsCard(
                title = article.title,
                thumbnail_url = article.thumbnailUrl,
                thumbnail_alt_text = article.thumbnailDescription
            )
        }
        // Footer
        item {
            Row(modifier = Modifier) {

            }
        }
    }
}