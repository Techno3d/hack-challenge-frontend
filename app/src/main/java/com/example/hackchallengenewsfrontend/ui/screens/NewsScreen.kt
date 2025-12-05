package com.example.hackchallengenewsfrontend.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hackchallengenewsfrontend.ui.components.CompactNewsCard
import com.example.hackchallengenewsfrontend.ui.components.NewsCard
import com.example.hackchallengenewsfrontend.ui.theme.Background
import com.example.hackchallengenewsfrontend.ui.theme.Secondary
import com.example.hackchallengenewsfrontend.viewmodels.NewsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsScreen(
    viewArticle: (String) -> Unit,
    newsViewModel: NewsViewModel = hiltViewModel<NewsViewModel>()
) {
    val popularItemsList = 2
    val uiState by newsViewModel.uiStateFlow.collectAsStateWithLifecycle()
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background)
            .padding(top=30.dp, start = 22.dp, end = 22.dp)
    ) {
        item {
            // Header
            Text(
                "Scope",
                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                fontSize = 36.sp,
                textAlign = TextAlign.Left,
                fontStyle = FontStyle.Italic,
                color = Color.White
            )
            Text(
                "Home",
                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                fontSize = 36.sp,
                textAlign = TextAlign.Left,
                color = Secondary
            )
            Spacer(Modifier.height(12.dp))
            //TODO Change FilterRow to be not hard-coded
//            FilterRow(
//                filters = listOf("Rock", "Hello", "CU Nooz"),
//                currentFiltersSelected = listOf("CU Nooz")
//            ) { }
            Spacer(Modifier.height(24.dp))
            Text(
                "Top Stories",
                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                fontSize = 20.sp,
                textAlign = TextAlign.Left,
                color = Color.White
            )
            Spacer(Modifier.height(12.dp))

            // Body
            //TODO Change NewsCard/CompactNewsCard to be not hard-coded
            if (!uiState.feed.isEmpty()) {
                val firstNewsCard = uiState.feed[0]
                NewsCard(
                    title = firstNewsCard.title,
                    thumbnailUrl = firstNewsCard.thumbnailUrl ?: "",
                    thumbnailDescription = firstNewsCard.thumbnailDescription,
                    author = firstNewsCard.author,
                    newsSource = firstNewsCard.newsSource,
                    date = firstNewsCard.date.toString(),
                    onCardClick = { viewArticle(firstNewsCard.articleUrl) }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
            if (uiState.feed.size < popularItemsList) return@item
            val laterItems = uiState.feed.slice(1..popularItemsList)
            Row {
                for (i in 0..1) {
                    Column(modifier = Modifier.weight(0.5f)) {
                        for (j in i..(laterItems.size - 1) step 2) {
                            NewsCard(
                                title = laterItems[j].title,
                                thumbnailUrl = laterItems[j].thumbnailUrl ?: "",
                                thumbnailDescription = laterItems[j].thumbnailDescription,
                                author = laterItems[j].author,
                                newsSource = laterItems[j].newsSource,
                                date = laterItems[j].date.toString(),
                                onCardClick = { viewArticle(laterItems[j].articleUrl) }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text("Popular Stories", fontSize = 25.sp)
        }
        if (uiState.feed.size < popularItemsList+1) return@LazyColumn
        val popularItems = uiState.feed.slice((popularItemsList+1)..(uiState.feed.size - 1))
        items(popularItems) { article ->
            CompactNewsCard(
                title = article.title,
                newsSource = article.newsSource,
                date = article.date.toString(),
                author = article.author,
                thumbnailUrl = article.thumbnailUrl ?: "",
                thumbnailDescription = article.thumbnailDescription ?: "",
                onCardClick = { viewArticle(article.articleUrl) }
            )
        }
    }
}