package com.example.hackchallengenewsfrontend.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hackchallengenewsfrontend.R
import com.example.hackchallengenewsfrontend.screens.IndividualListenScreen
import com.example.hackchallengenewsfrontend.screens.SavedScreen
import com.example.hackchallengenewsfrontend.ui.screens.ArticleViewScreen
import com.example.hackchallengenewsfrontend.ui.screens.MainListenScreen
import com.example.hackchallengenewsfrontend.ui.screens.NewsScreen
import com.example.hackchallengenewsfrontend.viewmodels.ArticleViewModel
import com.example.hackchallengenewsfrontend.viewmodels.PlayerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScopeApp() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController = navController) }) { innerPadding ->
        Box(modifier = Modifier.padding(top = innerPadding.calculateTopPadding())) {
            SetupNavHost(navController = navController)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val colors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.White,
        selectedTextColor = Color.White,
        unselectedIconColor = Color.LightGray,
        unselectedTextColor = Color.LightGray
    )

    NavigationBar(
        containerColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "Home"
                )
            },
            label = { Text("Home") },
            colors = colors,
            selected = currentRoute.equals("NewsScreen"),
            onClick = { navController.navigate("NewsScreen") }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_headphone),
                    contentDescription = "Audio"
                )
            },
            label = { Text("Audio") },
            colors = colors,
            selected = currentRoute.equals("audio"),
            onClick = { navController.navigate("audio") }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bookmark),
                    contentDescription = "Saved"
                )
            },
            label = { Text("Saved") },
            colors = colors,
            selected = currentRoute.equals("saved"),
            onClick = { navController.navigate("saved") }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavHost(
    navController: NavHostController,
    playerViewModel: PlayerViewModel = hiltViewModel<PlayerViewModel>()
) {
    val exoPlayer = playerViewModel.playerState.collectAsStateWithLifecycle()
    var selectedArticleId by remember { mutableStateOf<Int?>(null) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    NavHost(
        navController = navController,
        startDestination = "NewsScreen"
    ) {
        composable("NewsScreen") {
            NewsScreen(viewArticle = { id ->
                navController.navigate("article/$id")
            })
        }
        composable(
            "article/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            ArticleViewScreen(backStackEntry.arguments?.getInt("id") ?: 0) {
                navController.navigate("NewsScreen")
            }
        }
        composable(
            "audio",
        ) {
            exoPlayer?.let { player ->
                MainListenScreen(onCardClick = { id ->
                    selectedArticleId = id
                })

                LaunchedEffect(selectedArticleId) {
                    if (selectedArticleId == null) {
                        playerViewModel.stop()
                    }
                }

                // Show bottom sheet when article is selected
                selectedArticleId?.let { articleId ->
                    ModalBottomSheet(
                        onDismissRequest = {
                            playerViewModel.stop()
                            selectedArticleId = null
                        },
                        sheetState = sheetState,
                        containerColor = Color.Black
                    ) {
                        IndividualListenScreen(
                            articleID = selectedArticleId!!,
                            playerViewModel = playerViewModel
                        )
                    }
                }
            }
        }
            composable(
                "saved",
            ) {
                SavedScreen() { id -> navController.navigate("article/$id") }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    Scaffold(bottomBar = { BottomNavigationBar(navController = rememberNavController()) }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
        }
    }
}