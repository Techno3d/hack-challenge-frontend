package com.example.hackchallengenewsfrontend.networking

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: Int,
    val title: String,
    val link: String,
    val text: String = "",
    val author: String,
    @SerialName("pub_date")
    val publishingDate: String,
    @SerialName("image_url")
    val imageUrl: String?,
    @SerialName("audio_file")
    val audioFile: String?,
    val outlet: Outlet,
    val saved: Boolean? = false
)

@Serializable
data class Outlet(
    val id: Int,
    val name: String,
//    val slug: String?,
//    @SerialName("rss_feed")
//    val rssFeed: String?,
//    val url: String?,
//    val description: String?,
//    @SerialName("logo_url")
//    val logoUrl: String?,
)

@Serializable
data class User (
    val id: Int,
    val username: String,
    val email: String,
)

@Serializable
data class Message (
    val message: String,
    @SerialName("audio_file")
    val audioFile: String? = "",
    val user: User = User(id = 2, username = "johndoe", email = "john@example.com")
)

@Serializable
data class Auth(
    val username: String,
    val password: String,
)

@Serializable
data class CreateUserModel(
    val username: String,
    val email: String,
    val password: String,
)