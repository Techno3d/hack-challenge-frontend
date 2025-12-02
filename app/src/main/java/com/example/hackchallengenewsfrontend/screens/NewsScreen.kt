package com.example.hackchallengenewsfrontend.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.hackchallengenewsfrontend.components.CompactNewsCard
import com.example.hackchallengenewsfrontend.components.FilterRow
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
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(top=20.dp)
    ) {
        // Header
        item {
            Text("Scope", modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp), fontSize = 40.sp, textAlign = TextAlign.Left, color = Color.White)
            Text("Home", modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp), fontSize = 20.sp, textAlign = TextAlign.Left, color = Color.LightGray)
            Spacer(Modifier.height(12.dp))
            FilterRow(
                filters = listOf("Rock", "Hello", "CU Nooz"),
                currentFiltersSelected = listOf("CU Nooz")
            ) { }
            Spacer(Modifier.height(24.dp))
            Text("Top Stories", modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp), fontSize = 20.sp, textAlign = TextAlign.Left, color = Color.White)
            Spacer(Modifier.height(12.dp))
        }
        // Body
        item {
            NewsCard("Winter storm snarls flights for post-Thanksgiving travelers in Chicago",
                "https://d3i6fh83elv35t.cloudfront.net/static/2025/11/GettyImages-2248617554-1200x800.jpg",
                "Winter Storm Snarls Air Travel In Chicago",
                {viewArticle("https://www.pbs.org/newshour/nation/winter-storm-snarls-flights-for-post-thanksgiving-travelers-in-chicago")})
        }
        items(uiState.filteredFeed) { article ->
            CompactNewsCard(
                title = article.title,
                newsSource = "Goofy",
                author = "Goofy 2",
                thumbnailUrl = article.thumbnailUrl,
                thumbnailDescription = article.thumbnailDescription,
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