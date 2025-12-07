package com.example.hackchallengenewsfrontend.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.hackchallengenewsfrontend.ui.theme.Primary

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hackchallengenewsfrontend.R
import com.example.hackchallengenewsfrontend.ui.components.CompactNewsCard
import com.example.hackchallengenewsfrontend.ui.components.NewsCard
import com.example.hackchallengenewsfrontend.ui.components.ScopeSearchBar
import com.example.hackchallengenewsfrontend.ui.theme.Background
import com.example.hackchallengenewsfrontend.ui.theme.Secondary
import com.example.hackchallengenewsfrontend.viewmodels.NewsViewModel
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsScreen(
    viewArticle: (Int) -> Unit,
    newsViewModel: NewsViewModel = hiltViewModel<NewsViewModel>()
) {
    val popularItemsList = 2
    val uiState by newsViewModel.uiStateFlow.collectAsStateWithLifecycle()
    val searchQuery by newsViewModel.searchQuery.collectAsStateWithLifecycle()
    var isSearching by remember { mutableStateOf(false) }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background)
            .padding(top=30.dp, start = 22.dp, end = 22.dp)
    ) {
        item {
            // Header
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
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
                        text = "Home",
                        modifier = Modifier.padding(vertical = 2.dp),
                        fontSize = 24.sp,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.SemiBold,
                        color = Secondary
                    )
                }
                IconButton(onClick = { isSearching = !isSearching }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_searchbutton),
                        contentDescription = "Search Button",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
            AnimatedVisibility(
                visible = isSearching,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column {
                    Spacer(Modifier.height(12.dp))
                    ScopeSearchBar(
                        query = searchQuery,
                        onQueryChange = { newsViewModel.updateSearchQuery(it) },
                    )
                }
            }

            Spacer(Modifier.height(12.dp))
            //TODO Change FilterRow to be not hard-coded
//            FilterRow(
//                filters = listOf("Rock", "Hello", "CU Nooz"),
//                currentFiltersSelected = listOf("CU Nooz")
//            ) { }
//          Spacer(Modifier.height(24.dp))
            Text(
                "Top Stories",
                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                fontSize = 36.sp,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(Modifier.height(12.dp))

            //Body
            if (!uiState.feed.isEmpty()) {
                val firstNewsCard = uiState.feed[0]
                NewsCard(
                    title = firstNewsCard.title,
                    thumbnailUrl = firstNewsCard.thumbnailUrl ?: "",
                    thumbnailDescription = firstNewsCard.thumbnailDescription,
                    author = firstNewsCard.author,
                    newsSource = firstNewsCard.newsSource,
                    date = firstNewsCard.date?.toHumanReadable() ?: firstNewsCard.date.toString(),
                    onCardClick = { viewArticle(firstNewsCard.id) },
                    isFavorited = firstNewsCard.saved,
                    onFavoriteClick = {newsViewModel.toggleFavorite(firstNewsCard.id, firstNewsCard.saved)},
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
            if(uiState.feed.size >= 3) {
                val laterItems = uiState.feed.slice(1..popularItemsList)
                Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                    for (i in 0..1) {
                        Column(modifier = Modifier.weight(0.5f)) {
                            for (j in i..(laterItems.size - 1) step 2) {
                                NewsCard(
                                    title = laterItems[j].title,
                                    thumbnailUrl = laterItems[j].thumbnailUrl ?: "",
                                    thumbnailDescription = laterItems[j].thumbnailDescription,
                                    author = laterItems[j].author,
                                    newsSource = laterItems[j].newsSource,
                                    date = laterItems[j].date?.toHumanReadable()
                                        ?: laterItems[j].date.toString(),
                                    isCompact = true,
                                    onCardClick = { viewArticle(laterItems[j].id) },
                                    isFavorited = laterItems[j].saved,
                                    onFavoriteClick = {
                                        newsViewModel.toggleFavorite(
                                            laterItems[j].id,
                                            laterItems[j].saved
                                        )
                                    },
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Popular Reads",
                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                fontSize = 28.sp,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
        if(uiState.feed.size > 3) {
            val popularItems = uiState.feed.slice((popularItemsList + 1)..(uiState.feed.size - 1))
            items(popularItems) { article ->
                CompactNewsCard(
                    title = article.title,
                    newsSource = article.newsSource,
                    date = article.date?.toHumanReadable() ?: article.date.toString(),
                    author = article.author,
                    thumbnailUrl = article.thumbnailUrl ?: "",
                    thumbnailDescription = article.thumbnailDescription ?: "",
                    onCardClick = { viewArticle(article.id) },
                    onFavoriteClick = { newsViewModel.toggleFavorite(article.id, article.saved) },
                    isFavorited = article.saved,
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}


// Lowk from copilot
@RequiresApi(Build.VERSION_CODES.O)
fun humanReadableTimeAgo(then: Instant, now: Instant = Instant.now()): String {
    val diffSeconds = Duration.between(then, now).seconds
    if (diffSeconds == 0L) return "just now"

    val seconds = abs(diffSeconds)

    val (value, unit) = when {
        seconds < 5 -> return "just now"
        seconds < 60 -> seconds to "s"
        seconds < 60 * 60 -> (seconds / 60) to "m"
        seconds < 60 * 60 * 24 -> (seconds / (60 * 60)) to "h"
        seconds < 60 * 60 * 24 * 7 -> (seconds / (60 * 60 * 24)) to "d"
        seconds < 60 * 60 * 24 * 30 -> (seconds / (60 * 60 * 24 * 7)) to "w"
        seconds < 60 * 60 * 24 * 365 -> (seconds / (60 * 60 * 24 * 30)) to "mo"
        else -> (seconds / (60 * 60 * 24 * 365)) to "y"
    }

    return "${value}${unit} ago"
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toHumanReadable(
    now: ZonedDateTime = ZonedDateTime.now(),
    zone: ZoneId = ZoneId.systemDefault()
): String {
    val thenInstant = this.atZone(zone).toInstant()
    return humanReadableTimeAgo(thenInstant, now.toInstant())
}