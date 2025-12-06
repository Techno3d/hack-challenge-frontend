package com.example.hackchallengenewsfrontend.networking

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.currentComposer
import com.example.hackchallengenewsfrontend.viewmodels.News
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.Response
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton
import androidx.media3.common.MediaItem
import androidx.core.net.toUri

@Singleton
class ArticleRepository @Inject constructor(
    private val articleApiService: ArticleApiService
) {
    data class LoginInfo (
        val isLoggedIn: Boolean = false,
        val currentUserName: String? = null,
        val currentEmail: String? = null
    )
    private val _loginInfoFlow = MutableStateFlow<LoginInfo>(LoginInfo())
    val loginInfo = _loginInfoFlow.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getAllArticles(): Result<List<News>> {
        val response = articleApiService.getAllArticles()
        if(!response.isSuccessful) return Result.failure(Throwable(message = response.errorBody().toString()))
        val articles = response.body()?.map { article ->
            News(
                title = article.title,
                author = article.author,
                newsSource = article.outlet.name,
                articleUrl = article.link,
                tags = listOf(article.outlet.name),
                thumbnailUrl = article.imageUrl,
                thumbnailDescription = null,
                date = LocalDateTime.parse(article.publishingDate),
                id = article.id,
                text = article.text,
                audioFileName = article.audioFile,
                saved = article.saved ?: false
            )
        }
        return Result.success(articles!!)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getKNewestArticles(numArticles: Int): Result<List<News>> {
        val response = articleApiService.getKNewestArticles(numArticles)
        if(!response.isSuccessful) return Result.failure(Throwable(message = response.errorBody().toString()))
        val articles = response.body()?.map { article ->
            News(
                title = article.title,
                author = article.author,
                newsSource = article.outlet.name,
                articleUrl = article.link,
                tags = listOf(article.outlet.name),
                thumbnailUrl = article.imageUrl,
                thumbnailDescription = null,
                date = LocalDateTime.parse(article.publishingDate),
                id = article.id,
                text = article.text,
                audioFileName = article.audioFile,
                saved = article.saved ?: false
            )
        }
        return Result.success(articles!!)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getArticleByID(articleID: Int): Result<News> {
        val response = articleApiService.getArticleByID(articleID)
        if(!response.isSuccessful) return Result.failure(Throwable(message = response.errorBody().toString()))
        val article = response.body()!!
        val articles = News(
                title = article.title,
                author = article.author,
                newsSource = article.outlet.name,
                articleUrl = article.link,
                tags = listOf(article.outlet.name),
                thumbnailUrl = article.imageUrl,
                thumbnailDescription = null,
                date = LocalDateTime.parse(article.publishingDate),
                id = article.id,
                text = article.text,
                audioFileName = article.audioFile,
                saved = article.saved ?: false
            )
        return Result.success(articles)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getSavedArticles(): Result<List<News>> {
        val response = articleApiService.getSavedArticles()
        if(!response.isSuccessful) return Result.failure(Throwable(message = response.errorBody().toString()))
        val articles = response.body()?.map { article ->
            News(
                title = article.title,
                author = article.author,
                newsSource = article.outlet.name,
                articleUrl = article.link,
                tags = listOf(article.outlet.name),
                thumbnailUrl = article.imageUrl,
                thumbnailDescription = null,
                date = LocalDateTime.parse(article.publishingDate),
                id = article.id,
                text = article.text,
                audioFileName = article.audioFile,
                saved = article.saved ?: false
            )
        }
        return Result.success(articles!!)
    }

    suspend fun favoriteArticle(articleID: Int): Result<Unit> {
        val response = articleApiService.favoriteArticle(articleID)
        return if(response.isSuccessful) Result.success(Unit) else Result.failure(Throwable(response.errorBody().toString()))
    }

    suspend fun unFavoriteArticle(articleID: Int): Result<Unit> {
        val response = articleApiService.unFavoriteArticle(articleID)
        return if(response.isSuccessful) Result.success(Unit) else Result.failure(Throwable(response.errorBody().toString()))
    }

    suspend fun generateAudio(articleID: Int): Result<String> {
        val response = articleApiService.generateAudio(articleID)
        if(!response.isSuccessful) return Result.failure(Throwable(message = response.errorBody().toString()))
        return if(response.body()?.audioFile != null)
            Result.success(response.body()?.audioFile!!)
        else
            Result.failure(Throwable("no audio file bruv init"))
    }

    suspend fun registerAccount(username: String, password: String, email: String): Result<Unit> {
        val response = articleApiService.registerAccount(CreateUserModel(username, email, password))
        return if(response.isSuccessful) Result.success(Unit) else Result.failure(Throwable(response.errorBody().toString()))
    }

    suspend fun login(username: String, password: String): Result<Unit> {
        val response = articleApiService.login(Auth(username, password))
        if(response.isSuccessful) {
            _loginInfoFlow.value = _loginInfoFlow.value.copy(
                isLoggedIn = true,
                currentUserName = response.body()?.user?.username,
                currentEmail = response.body()?.user?.email
            )
        }
        return if(response.isSuccessful) Result.success(Unit) else Result.failure(Throwable(response.errorBody().toString()))
    }

    suspend fun logout(): Result<Unit> {
        val response = articleApiService.logout()
        if(response.isSuccessful) {
            _loginInfoFlow.value = _loginInfoFlow.value.copy(
                isLoggedIn = false,
                currentUserName = null,
                currentEmail = null
            )
        }
        return if(response.isSuccessful) Result.success(Unit) else Result.failure(Throwable(response.errorBody().toString()))
    }

    suspend fun getCurrentUserInfo(): Result<User> {
        val response = articleApiService.getCurrentUser()
        if(response.isSuccessful) {
            return Result.success(response.body()!!)
        }
        return Result.failure(Throwable(response.errorBody()?.toString()))
    }

    suspend fun getAudio(articleID: Int): Result<MediaItem> {
        val response = articleApiService.getArticleByID(articleID)
        if(!response.isSuccessful) return Result.failure(Throwable(response.errorBody().toString()))
        val audioFileName = response.body()?.audioFile
        if(audioFileName == null) {
            articleApiService.generateAudio(articleID)
        }
        return Result.success(MediaItem.fromUri("http://35.186.167.11:5000/audios/$audioFileName".toUri()))
    }
}