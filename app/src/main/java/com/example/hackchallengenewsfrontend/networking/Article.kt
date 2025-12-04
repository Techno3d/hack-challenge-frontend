package com.example.hackchallengenewsfrontend.networking

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Articles(
    val articles: List<Article>
)

@Serializable
data class Article(
    val author: String,
    val description: String,
    val id: Int,
    @SerialName("image_url")
    val imageUrl: String,
    val link: String,
    val outlet: Outlet,
    @SerialName("pub_date")
    val publishingDate: String,
    val title: String
)

@Serializable
data class Outlet(
    val id: Int,
    val name: String
)