package com.example.hackchallengenewsfrontend.screens

import android.service.autofill.Validators.and
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt // Used for roundToInt
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter.State.Empty.painter
import com.example.hackchallengenewsfrontend.R
import com.example.hackchallengenewsfrontend.viewmodels.ArticleViewModel
import com.example.hackchallengenewsfrontend.viewmodels.AudioViewModel
import kotlin.times

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndividualListenScreen(
    articleID: Int,
    audioViewModel: AudioViewModel = hiltViewModel<AudioViewModel>(),
    articleViewModel: ArticleViewModel = hiltViewModel<ArticleViewModel>()
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Collect audio state
    val audioState by audioViewModel.uiStateFlow.collectAsStateWithLifecycle()

    // Collect article state
    val articleState by articleViewModel.uiStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(articleID) {
        audioViewModel.loadAudio(articleID)
    }

    // Helper function to format seconds to MM:SS
    fun formatTime(seconds: Float): String {
        val mins = (seconds / 60).toInt()
        val secs = (seconds % 60).toInt()
        return "%d:%02d".format(mins, secs)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(24.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Image...
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(bottom = 16.dp)
        ) {
            AsyncImage(
                model = articleState.mainImage,
                contentDescription = articleState.mainImageDescription ?: "Article thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFCCCCCC), shape = RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
            )
        }

        // Title and bookmark...
        Row {
            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = articleState.newsSource,
                    color = Color.White,
                    fontSize = 14.sp
                )
                Text(
                    text = articleState.title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(onClick = { articleViewModel.toggleFavorite(articleID, articleState.saved) }) {
                Icon(
                    painter = painterResource(R.drawable.ic_bookmark),
                    contentDescription = "Save",
                    tint = if(articleState.saved) Color.White else Color.LightGray,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        // Playback slider
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp)
        ) {
            Slider(
                value = audioState.currentPosition,
                onValueChange = { audioViewModel.seekTo(it) },
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Color.LightGray.copy(alpha = 0.5f)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formatTime(audioState.currentPosition * audioState.duration),
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
                Text(
                    text = formatTime(audioState.duration),
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
            }
        }
        // Playback controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { audioViewModel.skipBackward() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_skipbackward),
                    contentDescription = "Skip Back 15 Seconds",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(onClick = { audioViewModel.playPause() }) {
                Icon(
                    painter = if (audioState.isPlaying) painterResource(id = R.drawable.ic_pausebutton)
                    else painterResource(id = R.drawable.ic_playbutton),
                    contentDescription = if (audioState.isPlaying) "Pause" else "Play",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }

            IconButton(onClick = { audioViewModel.skipForward() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_skipforward),
                    contentDescription = "Skip Forward 15 Seconds",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        // Volume slider
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_volumedown),
                contentDescription = "Volume Down",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )

            Slider(
                value = audioState.volume,
                onValueChange = { audioViewModel.setVolume(it) },
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Color.LightGray.copy(alpha = 0.5f)
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_volumeup),
                contentDescription = "Volume Up",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun IndividualListenScreenPreview() {
    // IndividualListenScreen()
}