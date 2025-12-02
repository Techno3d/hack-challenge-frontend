package com.example.hackchallengenewsfrontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hackchallengenewsfrontend.screens.NewsScreen
import com.example.hackchallengenewsfrontend.ui.theme.HackChallengeNewsFrontendTheme
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HackChallengeNewsFrontendTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(top = innerPadding.calculateTopPadding())) {
                        NavHost(
                            navController = navController,
                            startDestination = "NewsScreen"
                        ) {
                            composable("NewsScreen") {
                                NewsScreen(viewArticle = { url ->
                                    val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
                                    navController.navigate("article/$encodedUrl")
                                })
                            }
                            composable(
                                "article/{url}",
                                arguments = listOf(navArgument("url") {type = NavType.StringType})
                            ) { backStackEntry ->
                                Text("This is the article screen for: ${backStackEntry.arguments?.getString("url")}")
                            }
                        }
                    }
                }
            }
        }
    }
}