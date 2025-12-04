package com.example.hackchallengenewsfrontend.screens

import android.R.attr.text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hackchallengenewsfrontend.viewmodels.ArticleViewModel

@Composable
fun ArticleViewScreen(
    articleViewModel: ArticleViewModel = hiltViewModel<ArticleViewModel>()
){
    val uiState by articleViewModel.uiStateFlow.collectAsStateWithLifecycle()
    ArticleViewContent(uiState = uiState)
}

@Composable
private fun ArticleViewContent(
    uiState: ArticleViewModel.ArticleUIState
){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(modifier = Modifier.fillMaxWidth()){
            //TODO: Figure out proper spacing technique later
            Spacer(modifier = Modifier.width(33.dp))
            Text(text = "Back Btn", color = Color.White)
            Spacer(modifier = Modifier.width(80.dp))
            Text(text = "News Source", color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(80.dp))
            Text(text = "Bookmark Btn", color = Color.White)
        }
        Text(text = "Title", color = Color.White)
        Text(text = "Article Description", color = Color.White)
        Text(text = "Date", color = Color.White)
        Text(text = "INSERT IMAGE", color = Color.White)
        Text(text = "Image Description", color = Color.White)
        Text(text = "By Author", color = Color.White)
        Text(text = "Article", color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleViewScreenPreview(){
    ArticleViewContent(
        uiState = ArticleViewModel.ArticleUIState()
    )

}