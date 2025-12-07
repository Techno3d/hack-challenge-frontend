package com.example.hackchallengenewsfrontend.ui.screens

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
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
import com.example.hackchallengenewsfrontend.ui.components.FilterRow
import com.example.hackchallengenewsfrontend.ui.components.NewsCard
import com.example.hackchallengenewsfrontend.ui.components.ScopeSearchBar
import com.example.hackchallengenewsfrontend.ui.theme.Primary
import com.example.hackchallengenewsfrontend.ui.theme.Secondary
import com.example.hackchallengenewsfrontend.viewmodels.NewsViewModel
import com.example.hackchallengenewsfrontend.viewmodels.PlayerViewModel
import kotlin.compareTo
import kotlin.text.get

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainListenScreen(
    onCardClick: (Int) -> Unit = {},
    newsViewModel: NewsViewModel = hiltViewModel<NewsViewModel>()
) {
    val uiState by newsViewModel.uiStateFlow.collectAsStateWithLifecycle()
    val searchQuery by newsViewModel.searchQuery.collectAsStateWithLifecycle()
    var isSearching by remember { mutableStateOf(false) }

    val audioArticles = uiState.feed.filter { true }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(top = 30.dp)
    ) {
        item {
            // Header with padding
            Column(modifier = Modifier.padding(horizontal = 22.dp)) {
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
                    IconButton(onClick = { isSearching = !isSearching }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_searchbutton),
                            contentDescription = "Search Button",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = isSearching,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(modifier = Modifier.padding(horizontal = 22.dp)) {
                    Spacer(Modifier.height(12.dp))
                    ScopeSearchBar(
                        query = searchQuery,
                        onQueryChange = { newsViewModel.updateSearchQuery(it) },
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            val availableOutlets by newsViewModel.availableOutlets.collectAsStateWithLifecycle()
            val selectedOutletId by newsViewModel.selectedOutletId.collectAsStateWithLifecycle()

            val outletFilters = remember(availableOutlets) {
                buildMap {
                    put(null, "All")
                    availableOutlets.forEach { outlet ->
                        put(outlet.id, outlet.name)
                    }
                }
            }

            if (outletFilters.size > 1) {
                FilterRow(
                    filters = outletFilters.values.toList(),
                    currentFiltersSelected = listOf(
                        outletFilters[selectedOutletId] ?: "All"
                    )
                ) { selectedFilter ->
                    val outletId = outletFilters.entries.find { it.value == selectedFilter }?.key
                    newsViewModel.selectOutlet(outletId)
                }
            }

            // Content with padding
            Column(modifier = Modifier.padding(horizontal = 22.dp)) {
                Spacer(Modifier.height(12.dp))
                Text(
                    "Editor's Picks",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    fontSize = 36.sp,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.height(12.dp))

                val animationMsDuration = 600

                AnimatedContent(
                    targetState = uiState.isLoading,
                    transitionSpec = {
                        if (targetState) {
                            fadeIn(animationSpec = tween(animationMsDuration)) togetherWith
                                    slideOutVertically(
                                        targetOffsetY = { fullHeight -> fullHeight },
                                        animationSpec = tween(animationMsDuration)
                                    ) + fadeOut(animationSpec = tween(animationMsDuration))
                        } else {
                            slideInVertically(
                                initialOffsetY = { fullHeight -> fullHeight },
                                animationSpec = tween(animationMsDuration)
                            ) + fadeIn(animationSpec = tween(animationMsDuration)) togetherWith
                                    fadeOut(animationSpec = tween(animationMsDuration))
                        }
                    },
                    label = "audio_loading_animation"
                ) { isLoading ->
                    if (isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(600.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator(
                                    color = Primary,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(Modifier.height(16.dp))
                                Text(
                                    text = "Loading audio articles...",
                                    color = Secondary,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    } else {
                        AnimatedContent(
                            targetState = selectedOutletId,
                            transitionSpec = {
                                fadeIn(animationSpec = tween(animationMsDuration)) togetherWith
                                        fadeOut(animationSpec = tween(animationMsDuration))
                            },
                            label = "audio_outlet_switch_animation"
                        ) { outletId ->
                            key(outletId) {
                                Column {
                                    // Editor's Picks horizontal scroll
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
                                                onFavoriteClick = { newsViewModel.toggleFavorite(article.id, article.saved) },
                                            )
                                            Spacer(modifier = Modifier.width(20.dp))
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(24.dp))

                                    Text(
                                        "Audio Stories For You",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 2.dp),
                                        fontSize = 28.sp,
                                        textAlign = TextAlign.Left,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        item{
            Spacer(modifier = Modifier.height(12.dp))
        }

        if(audioArticles.size > 3) {
            items(audioArticles.drop(3)) { article ->
                Column(modifier = Modifier.padding(horizontal = 22.dp)) {
                    CompactNewsCard(
                        title = article.title,
                        newsSource = article.newsSource,
                        author = article.author,
                        thumbnailUrl = article.thumbnailUrl ?: "",
                        thumbnailDescription = article.thumbnailDescription ?: "",
                        onCardClick = { onCardClick(article.id) },
                        date = article.date?.toHumanReadable() ?: "",
                        isAudio = true,
                        isFavorited = article.saved,
                        onFavoriteClick = { newsViewModel.toggleFavorite(article.id, article.saved) },
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}