package com.example.hackchallengenewsfrontend.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.hackchallengenewsfrontend.ui.components.CompactNewsCard
import com.example.hackchallengenewsfrontend.ui.components.NewsCard
import com.example.hackchallengenewsfrontend.viewmodels.NewsViewModel

@RequiresApi(Build.VERSION_CODES.O)
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
            .padding(top=30.dp, start = 22.dp, end = 22.dp)
    ) {
        // Header
        item {
            Text("Scope", modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp), fontSize = 40.sp, textAlign = TextAlign.Left, color = Color.White)
            Text("Home", modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp), fontSize = 20.sp, textAlign = TextAlign.Left, color = Color.LightGray)
            Spacer(Modifier.height(12.dp))
            //TODO Change FilterRow to be not hard-coded
//            FilterRow(
//                filters = listOf("Rock", "Hello", "CU Nooz"),
//                currentFiltersSelected = listOf("CU Nooz")
//            ) { }
            Spacer(Modifier.height(24.dp))
            Text("Top Stories", modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp), fontSize = 20.sp, textAlign = TextAlign.Left, color = Color.White)
            Spacer(Modifier.height(12.dp))
        }

        // Body
        //TODO Change NewsCard/CompactNewsCard to be not hard-coded
        if(!uiState.feed.isEmpty()) {
            val firstNewsCard = uiState.feed[0]
            item {
//            NewsCard(newsSource = "Cornell Chronicle",
//                title = "Winter storm snarls flights for post-Thanksgiving travelers in Chicago",
//                thumbnailUrl = "https://d3i6fh83elv35t.cloudfront.net/static/2025/11/GettyImages-2248617554-1200x800.jpg",
//                thumbnailDescription = "Winter Storm Snarls Air Travel In Chicago",
//                onCardClick = {viewArticle("https://www.pbs.org/newshour/nation/winter-storm-snarls-flights-for-post-thanksgiving-travelers-in-chicago")})
                NewsCard(
                    title = firstNewsCard.title,
                    thumbnailUrl = firstNewsCard.thumbnailUrl?:"",
                    thumbnailDescription = firstNewsCard.thumbnailDescription,
                    author = firstNewsCard.author,
                    newsSource = firstNewsCard.newsSource,
                    date = firstNewsCard.date.toString(),
                    onCardClick = {viewArticle(firstNewsCard.articleUrl)}
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
        items(uiState.feed.slice(1..(uiState.feed.size-1))) { article ->
            CompactNewsCard(
                title = article.title,
                newsSource = article.newsSource,
                date = article.date.toString(),
                author = article.author,
                thumbnailUrl = article.thumbnailUrl?:"",
                thumbnailDescription = article.thumbnailDescription ?: "",
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