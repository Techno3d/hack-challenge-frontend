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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import coil3.compose.AsyncImagePainter.State.Empty.painter
import com.example.hackchallengenewsfrontend.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndividualListenScreen() {
    // State to manage the Sheet's visibility (not directly in this Composable, but needed for context)
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Internal States for the UI elements
    //TODO: VOLUME LEVEL BASED ON PHONE VOLUME LEVEL
    var playbackPosition by remember { mutableFloatStateOf(0.0f) } // 0.0 to 1.0
    var volumeLevel by remember { mutableFloatStateOf(0.0f) }    // 0.0 to 1.0
    var isPlaying by remember { mutableStateOf(false) }

    // Use a placeholder for the image resource
    val imagePlaceholder = Color(0xFFCCCCCC)

    // The main container for the modal content
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black) // Dark background to match the image
            .padding(24.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- 1. Audio Thumbnail / Header ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(bottom = 16.dp)
        ) {
            // Placeholder for the main image
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(imagePlaceholder, shape = RoundedCornerShape(8.dp))
            )
        }

        // --- 2. Title and Subtitle ---
        Row() {
            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Scope Audio",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Text(
                    text = "Title Title Title Title title title tiel tiel title itielti ",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Bookmark icon (top right)
            Icon(
                painter = painterResource(R.drawable.ic_bookmark),
                contentDescription = "Save",
                tint = Color.White,
                modifier = Modifier
                    .padding(12.dp)
            )
        }

        // --- 3. Playback Slider / Progress Bar ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp)
        ) {
            Slider(
                value = playbackPosition,
                onValueChange = { playbackPosition = it },
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
                // Current Time
                //TODO: MAKE THIS NOT HARD-CODED
                Text(
                    text = "video start 0 0 0 ",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
                // Total Time
                Text(text = "video length end", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
            }
        }

        // --- 4. Playback Controls ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Skip Back 15s
            IconButton(onClick = { /* Handle skip back */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_skipbackward),
                    contentDescription = "Skip Back 15 Seconds",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            // Play / Pause Button
            IconButton(onClick = { isPlaying = !isPlaying }) {
                Icon(
                    painter = if (isPlaying) painterResource(id = R.drawable.ic_pausebutton)
                    else painterResource(id = R.drawable.ic_playbutton),
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }

            // Skip Forward 15s
            IconButton(onClick = { /* Handle skip forward */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_skipforward),
                    contentDescription = "Skip Forward 15 Seconds",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        // --- 5. Volume Slider ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Volume Down Icon
            Icon(
                painter = painterResource(id = R.drawable.ic_volumedown),
                contentDescription = "Volume Down",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )

            // Volume Slider
            Slider(
                value = volumeLevel,
                onValueChange = { volumeLevel = it },
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Color.LightGray.copy(alpha = 0.5f)
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

            // Volume Up Icon
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
    IndividualListenScreen()
}