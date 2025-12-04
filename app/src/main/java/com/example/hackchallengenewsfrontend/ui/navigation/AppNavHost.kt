package com.example.hackchallengenewsfrontend.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hackchallengenewsfrontend.ui.screens.NewsScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScopeApp (){
    val navController = rememberNavController()
    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController = navController) }) { innerPadding ->
        Box(modifier = Modifier.padding(top = innerPadding.calculateTopPadding())) {
            SetupNavHost(navController = navController)
        }
    }
}

@Composable
fun BottomNavigationBar(navController : NavHostController){
    val colors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.White,
        selectedTextColor = Color.White,
        unselectedIconColor = Color.LightGray,
        unselectedTextColor = Color.LightGray
    )

    NavigationBar(
        containerColor = Color.Black
    ){
        // TODO: Add selected state, change selected icon color to only fill icon, not icon background
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            colors = colors,
            selected = true,
            onClick = { /* TODO: Navigate to Home */ }
        )

        // TODO: Add real icon + selected state
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Phone, contentDescription = "Search") },
            label = { Text("Audio") },
            colors = colors,
            selected = false,
            onClick = { /* TODO: Navigate to Audio */ }
        )

        //TODO: Add real icon + selected state
        NavigationBarItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Profile") },
            label = { Text("Saved") },
            colors = colors,
            selected = false,
            onClick = { /* TODO: Navigate to Saved */ }
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavHost(navController : NavHostController){
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

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview(){
    Scaffold(bottomBar = { BottomNavigationBar(navController = rememberNavController()) }){ innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)){
        }
    }
}