package com.example.hackchallengenewsfrontend.networking

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.hackchallengenewsfrontend.viewmodels.News
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepository @Inject constructor(
    private val articleApiService: ArticleApiService
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAllArticles(): Result<List<News>> {
        val response = articleApiService.getAllArticles()
        if(!response.isSuccessful) return Result.failure(Throwable(message = response.errorBody().toString()))
        val articles = response.body()?.articles!!.map { article ->
            News(
                title = article.title,
                author = article.author,
                newsSource = article.outlet.name,
                articleUrl = article.link,
                tags = listOf(article.outlet.name),
                thumbnailUrl = article.imageUrl,
                thumbnailDescription = null,
                date = LocalDate.parse(article.publishingDate)
            )
        }
        return Result.success(articles)
    }
}