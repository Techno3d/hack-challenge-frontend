package com.example.hackchallengenewsfrontend.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _playerState = MutableStateFlow<ExoPlayer?>(null)
    val playerState: StateFlow<ExoPlayer?> = _playerState

    val exoPlayer: ExoPlayer by lazy {
        ExoPlayer.Builder(context).build()
    }
    
    init {
        _playerState.value = exoPlayer
    }

    fun loadAudio(media: MediaItem) {
        viewModelScope.launch {
            exoPlayer.stop()
            exoPlayer.apply {
                setMediaItem(media)
                prepare()
            }
        }
    }
}