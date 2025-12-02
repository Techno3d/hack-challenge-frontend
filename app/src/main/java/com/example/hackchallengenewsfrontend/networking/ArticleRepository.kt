package com.example.hackchallengenewsfrontend.networking

import com.example.hackchallengenewsfrontend.viewmodels.News
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepository @Inject constructor(

) {
    suspend fun getAllArticles(): Result<List<News>> {
        // TODO: Actually do this lmao :skull:
        return Result.success(emptyList<News>())
    }
}