package com.example.hackchallengenewsfrontend.viewmodels

import java.util.Date

data class News (
    val title: String,
    val author: String,
    val newsSource: String,
    val articleUrl: String,
    val tags: List<NewsTags>,
    val thumbnailUrl: String,
    val thumbnailDescription: String,
    val date: Date,
)

enum class NewsTags {
    Today, Athletics, Research
}
