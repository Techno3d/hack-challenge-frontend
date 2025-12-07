package com.example.hackchallengenewsfrontend.utils

import com.example.hackchallengenewsfrontend.R

fun getLogoForSource(sourceName: String): Int {
    return when {
        sourceName.contains("Cornell Daily Sun") -> R.drawable.cornell_daily_sun_logo
        sourceName.contains("Cornell Chronicle") -> R.drawable.cornell_chronicle_logo
        sourceName.contains("The Ithaca Voice") -> R.drawable.ithaca_voice_logo
        sourceName.contains("14850") -> R.drawable.ithaca_14850_logo
        else -> R.drawable.ic_launcher_foreground
    }
}