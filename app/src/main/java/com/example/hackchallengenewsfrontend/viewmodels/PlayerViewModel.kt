package com.example.hackchallengenewsfrontend.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.hackchallengenewsfrontend.networking.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.text.toLong

@HiltViewModel
class PlayerViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val articleRepository: ArticleRepository
) : ViewModel() {
    private val _playerState = MutableStateFlow<ExoPlayer?>(null)
    val playerState: StateFlow<ExoPlayer?> = _playerState
    private val _uiStateFlow = MutableStateFlow(AudioUIState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    data class AudioUIState(
        val currentPosition: Long = 0L,
        val duration: Long = 225000L, // Default 3:45 in seconds
        val volume: Float = 1f,
        val isPlaying: Boolean = false,
        val currentAudio: MediaItem? = null,
    )

    val exoPlayer: ExoPlayer by lazy {
        ExoPlayer.Builder(context).build()
    }
    
    init {
        _playerState.value = exoPlayer
        startPositionUpdates()
        _uiStateFlow.value = _uiStateFlow.value.copy(
            currentPosition = _playerState.value?.currentPosition ?: 0,
            duration = _playerState.value?.duration ?: 0,
            volume = _playerState.value?.volume ?: 0f,
            isPlaying = _playerState.value?.isPlaying ?: false,
        )
    }

    private fun startPositionUpdates(){
        viewModelScope.launch {
            while(true) {
                delay(100)
                _uiStateFlow.update { currentState ->
                    currentState.copy(
                        currentPosition = exoPlayer.currentPosition,
                        duration = if (exoPlayer.duration > 0) exoPlayer.duration else currentState.duration,
                        isPlaying = exoPlayer.isPlaying,
                    )
                }
            }
        }
    }

    fun loadAudio(articleID: Int) {
        viewModelScope.launch {
            exoPlayer.stop()
            val response = articleRepository.getAudio(articleID)
            response.onSuccess { audio ->
                _uiStateFlow.value = _uiStateFlow.value.copy(
                    currentAudio = audio,
                    duration = 225000L,
                    isPlaying = false,
                    currentPosition = 0L
                )
                exoPlayer.apply {
                    setMediaItem(audio)
                    prepare()
                }
                print(exoPlayer.duration)
            }
                .onFailure { error ->
                    "we fucked up the audio generation"
                }
        }
    }

    fun playPause() {
        if(_uiStateFlow.value.isPlaying) exoPlayer.pause()
        else exoPlayer.play()

        _uiStateFlow.value = _uiStateFlow.value.copy(
            isPlaying = !_uiStateFlow.value.isPlaying
        )
    }

    fun stop() {
        exoPlayer.stop()
        _uiStateFlow.value = _uiStateFlow.value.copy(
            isPlaying = false,
            currentPosition = 0L,
            currentAudio = null
        )
    }

    fun seekTo(position: Float) {
        exoPlayer.seekTo((position * _uiStateFlow.value.duration).toLong())
    }

    fun setVolume(volume: Float) {
        exoPlayer.volume = volume
        _uiStateFlow.value = _uiStateFlow.value.copy(
            volume = volume
        )
    }

    fun skipForward(millis: Long = 15000) {
        val newPosition = _uiStateFlow.value.currentPosition + millis
        exoPlayer.seekTo(newPosition)

    }

    fun skipBackward(millis: Long = 15000) {
        val newPosition = _uiStateFlow.value.currentPosition - millis
        exoPlayer.seekTo(newPosition)
    }
}