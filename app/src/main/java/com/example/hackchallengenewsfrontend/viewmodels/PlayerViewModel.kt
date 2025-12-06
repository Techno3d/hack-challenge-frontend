package com.example.hackchallengenewsfrontend.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor() : ViewModel() {
    private val _playerState = MutableStateFlow<ExoPlayer?>(null)
    val playerState: StateFlow<ExoPlayer?> = _playerState

    fun createPlayerWithMediaItems(context: Context,) {
        if (_playerState.value == null) {
            // Create the player instance and update it to UI via stateFlow
            _playerState.update {
                ExoPlayer.Builder(context).build()
            }
        }
    }
}