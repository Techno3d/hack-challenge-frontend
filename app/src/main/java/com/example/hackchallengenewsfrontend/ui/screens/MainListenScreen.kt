package com.example.hackchallengenewsfrontend.ui.screens

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes.isAudio
import androidx.media3.exoplayer.ExoPlayer
import com.example.hackchallengenewsfrontend.R
import com.example.hackchallengenewsfrontend.ui.components.CompactNewsCard
import com.example.hackchallengenewsfrontend.ui.components.NewsCard
import com.example.hackchallengenewsfrontend.ui.theme.Primary
import com.example.hackchallengenewsfrontend.ui.theme.Secondary
import com.example.hackchallengenewsfrontend.viewmodels.NewsViewModel
import com.example.hackchallengenewsfrontend.viewmodels.PlayerViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainListenScreen(
    onCardClick: (Int) -> Unit = {},
    playerViewModel: PlayerViewModel = hiltViewModel<PlayerViewModel>(),
    newsViewModel: NewsViewModel = hiltViewModel<NewsViewModel>()
) {
    val uiState by newsViewModel.uiStateFlow.collectAsStateWithLifecycle()

    // Filter articles that have audio
    val audioArticles = uiState.feed.filter { article ->
        // TODO: Replace with actual audio field check when backend provides it
        // article.audioUrl != null || article.hasAudio == true
        true // For now, showing all articles
    }

    playerViewModel.loadAndPlayAudio(MediaItem.fromUri(("http://35.186.167.11:5000/audios/51.mp3")))

    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(top = 30.dp, start = 22.dp, end = 22.dp)
    ) {
        // Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Scope",
                        modifier = Modifier.padding(vertical = 2.dp),
                        fontSize = 36.sp,
                        textAlign = TextAlign.Left,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                    Text(
                        text = "Audio",
                        modifier = Modifier.padding(vertical = 2.dp),
                        fontSize = 24.sp,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.SemiBold,
                        color = Secondary
                    )
                }
                IconButton(onClick = {/* TODO: Search Functionality */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_searchbutton),
                        contentDescription = "Search Button",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
            Text(
                "Editor's Picks",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                fontSize = 20.sp,
                textAlign = TextAlign.Left,
                color = Color.White
            )
            Spacer(Modifier.height(12.dp))
        }

        // Body
        //TODO Change NewsCard/CompactNewsCard to be not hard-coded
        item {
            Row(
                modifier = Modifier
                    .horizontalScroll(state = rememberScrollState())
                    .fillMaxWidth()
            ) {
                audioArticles.take(3).forEach { article ->
                    NewsCard(
                        newsSource = article.newsSource,
                        title = article.title,
                        thumbnailUrl = article.thumbnailUrl ?: "",
                        thumbnailDescription = article.thumbnailDescription ?: "",
                        onCardClick = { onCardClick(article.id) },
                        author = article.author,
                        date = article.date?.toHumanReadable() ?: "",
                        modifier = Modifier.width(300.dp),
                        isAudio = true,
                        isFavorited = article.saved,
                        onFavoriteClick = {newsViewModel.toggleFavorite(article.id, article.saved)},
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Text(
                "Audio Stories For You",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                fontSize = 20.sp,
                textAlign = TextAlign.Left,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        items(audioArticles.drop(3)) { article ->
            CompactNewsCard(
                title = article.title,
                newsSource = article.newsSource,
                author = article.author,
                thumbnailUrl = article.thumbnailUrl ?: "",
                thumbnailDescription = article.thumbnailDescription ?: "",
                onCardClick = {onCardClick(article.id)},
                date = article.date?.toHumanReadable() ?: "",
                isAudio = true,
                isFavorited = article.saved,
                onFavoriteClick = {newsViewModel.toggleFavorite(article.id, article.saved)},
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}