package com.example.hackchallengenewsfrontend.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor() : ViewModel() {
    private val _uiStateFlow = MutableStateFlow(AudioUIState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    data class AudioUIState(
        val currentPosition: Float = 0f,
        val duration: Float = 225f, // Default 3:45 in seconds
        val volume: Float = 1f,
        val isPlaying: Boolean = false,
        val currentAudioUrl: String? = null
    )

    fun loadAudio(url: String) {
        viewModelScope.launch {
            _uiStateFlow.value = _uiStateFlow.value.copy(
                currentAudioUrl = url,
                currentPosition = 0f
            )
            // TODO: Fetch actual audio duration from MediaPlayer
        }
    }

    fun playPause() {
        _uiStateFlow.value = _uiStateFlow.value.copy(
            isPlaying = !_uiStateFlow.value.isPlaying
        )
    }

    fun seekTo(position: Float) {
        _uiStateFlow.value = _uiStateFlow.value.copy(
            currentPosition = position
        )
    }

    fun setVolume(volume: Float) {
        _uiStateFlow.value = _uiStateFlow.value.copy(
            volume = volume
        )
    }

    fun skipForward(seconds: Float = 15f) {
        val newPosition = (_uiStateFlow.value.currentPosition + (seconds / _uiStateFlow.value.duration)).coerceIn(0f, 1f)
        _uiStateFlow.value = _uiStateFlow.value.copy(currentPosition = newPosition)
    }

    fun skipBackward(seconds: Float = 15f) {
        val newPosition = (_uiStateFlow.value.currentPosition - (seconds / _uiStateFlow.value.duration)).coerceIn(0f, 1f)
        _uiStateFlow.value = _uiStateFlow.value.copy(currentPosition = newPosition)
    }
}