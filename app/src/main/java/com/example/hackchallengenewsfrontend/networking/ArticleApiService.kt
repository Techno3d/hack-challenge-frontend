package com.example.hackchallengenewsfrontend.networking

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Streaming

interface ArticleApiService {
    @GET("articles")
    suspend fun getAllArticles(): Response<List<Article>>

    @GET("articles/{article_id}")
    suspend fun getArticleByID(
        @Path("article_id") articleId: Int
    ): Response<Article>

    @GET("articles/top/{top_k}")
    suspend fun getKNewestArticles(
        @Path("top_k") numArticles: Int
    ): Response<List<Article>>

    @GET("articles/saved")
    suspend fun getSavedArticles(): Response<List<Article>>

    @POST("articles/{article_id}/save")
    suspend fun favoriteArticle(
        @Path("article_id") articleId: Int
    ): Response<Message>

    @DELETE("articles/{article_id}/unsave")
    suspend fun unFavoriteArticle(
        @Path("article_id") articleId: Int
    ): Response<Message>

    @POST("articles/{article_id}/generate-audio")
    suspend fun generateAudio(
        @Path("article_id") articleId: Int
    ): Response<Message>

//    @Streaming
//    @GET("audios/{filename}")
//    suspend fun serveAudio(
//        @Path("filename") filename: Int
//    ): Response<ResponseBody>

    @POST("/auth/register")
    suspend fun registerAccount(@Body account: CreateUserModel): Response<Message>

    @POST("/auth/login")
    suspend fun login(@Body account: Auth): Response<Message>

    @POST("/auth/logout")
    suspend fun logout(): Response<Message>

    @GET("/auth/me")
    suspend fun getCurrentUser(): Response<User>
}