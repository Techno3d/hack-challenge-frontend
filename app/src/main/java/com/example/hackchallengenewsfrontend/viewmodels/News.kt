package com.example.hackchallengenewsfrontend.viewmodels

import java.time.LocalDateTime

data class News (
    val title: String,
    val author: String,
    val newsSource: String,
    val articleUrl: String,
    val tags: List<String>,
    val thumbnailUrl: String?,
    val thumbnailDescription: String?,
    val date: LocalDateTime?,
)