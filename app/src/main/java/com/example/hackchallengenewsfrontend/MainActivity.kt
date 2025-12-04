package com.example.hackchallengenewsfrontend

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.example.hackchallengenewsfrontend.ui.navigation.ScopeApp
import com.example.hackchallengenewsfrontend.ui.theme.HackChallengeNewsFrontendTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HackChallengeNewsFrontendTheme {
                ScopeApp()
            }
        }
    }
}