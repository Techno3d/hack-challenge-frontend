package com.example.hackchallengenewsfrontend.networking

import retrofit2.Response
import retrofit2.http.GET

interface ArticleApiService {
    @GET("articles")
    suspend fun getAllArticles(): Response<Articles>
}