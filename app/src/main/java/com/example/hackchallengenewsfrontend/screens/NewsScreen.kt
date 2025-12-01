package com.example.hackchallengenewsfrontend.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hackchallengenewsfrontend.components.NewsCard
import com.example.hackchallengenewsfrontend.components.NewsCardPreview
import com.example.hackchallengenewsfrontend.viewmodels.NewsViewModel

@Composable
fun NewsScreen(
    viewArticle: (String) -> Unit,
    newsViewModel: NewsViewModel = hiltViewModel<NewsViewModel>()
) {
    val uiState by newsViewModel.uiStateFlow.collectAsStateWithLifecycle()
    LazyColumn(
        Modifier
            .padding(top=10.dp)
    ) {
        // Header
        item {
            Text("(Name of App)", modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), fontSize = 25.sp, textAlign = TextAlign.Center)
            Spacer(Modifier.height(4.dp).fillMaxWidth().background(color = Color.Black).padding(vertical = 5.dp))
            NewsCardPreview()
        }
        // Body
        items(uiState.filteredFeed) { article ->
            NewsCard(
                title = article.title,
                thumbnail_url = article.thumbnailUrl,
                thumbnail_alt_text = article.thumbnailDescription,
                onCardClick = {viewArticle(article.articleUrl)}
            )
        }
        // Footer
        item {
            Row(modifier = Modifier) {

            }
        }
    }
}