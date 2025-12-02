package com.example.hackchallengenewsfrontend.viewmodels

data class News (
    val title: String,
    val author: String,
    val articleUrl: String,
    val tags: List<NewsTags>,
    val thumbnailUrl: String,
    val thumbnailDescription: String,
)

enum class NewsTags {
    Today, Athletics, Research
}
