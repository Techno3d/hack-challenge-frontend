package com.example.hackchallengenewsfrontend.ui.screens

import android.R.attr.contentDescription
import android.R.attr.text
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter.State.Empty.painter
import com.example.hackchallengenewsfrontend.R
import com.example.hackchallengenewsfrontend.viewmodels.ArticleViewModel
import kotlinx.coroutines.coroutineScope

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticleViewScreen(
    articleId: Int,
    articleViewModel: ArticleViewModel = hiltViewModel<ArticleViewModel>(),
    navHome: () -> Unit
) {
    val uiState by articleViewModel.uiStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        articleViewModel.loadArticle(articleId)
    }
    ArticleViewContent(uiState = uiState, navHome)
}

@Composable
private fun ArticleViewContent(
    uiState: ArticleViewModel.ArticleUIState,
    navHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp)
            .verticalScroll(state = rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = navHome) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back Button",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }

            Text(text = uiState.newsSource, color = Color.White, fontSize = 20.sp)

            //Remember to change color of bookmark button when applicable
            IconButton(onClick = {/* TODO: Save Functionality */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bookmark),
                    contentDescription = "Save Button",
                    tint = if(uiState.saved) Color.White else Color.LightGray,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        Text(
            text = uiState.title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

//        Spacer(modifier = Modifier.height(12.dp))
//
//        Text(text = "Article Description", color = Color.White, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = uiState.date, color = Color.LightGray, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(12.dp))

        //Placeholder image for viewing purposes
        AsyncImage(
            model = uiState.mainImage,
            contentDescription = uiState.mainImageDescription,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(bottom = 16.dp)
        )
        Text(text = uiState.mainImageDescription ?: "", color = Color.LightGray, fontSize = 12.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "By ${uiState.author}", color = Color.White, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = uiState.articleText, color = Color.White, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(120.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleViewScreenPreview() {
    ArticleViewContent(
        uiState = ArticleViewModel.ArticleUIState()
    ) {}
}

@Preview(showBackground = true)
@Composable
fun TestingArticleView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp)
            .verticalScroll(state = rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Btn", color = Color.White)
            Text(text = "News Source", color = Color.White)
            Text(text = "Btn", color = Color.White)
        }

        Spacer(modifier = Modifier.height(36.dp))

        Text(
            text = "We do a little trolling! This is a long string of text for the title hahahahaha hahhahaha",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Article description Article description Article description Article description Article description Article description Article description Article description Article description ",
            color = Color.White,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "Date", color = Color.LightGray, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "INSERT IMAGE", color = Color.White)

        Text(
            text = "Image Description Image Description Image Description Image Description Image Description Image Description Image Description",
            color = Color.LightGray,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "By Author", color = Color.White, fontSize = 15.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "man & girl go out to drive under moonlight. they stop at on at a side of road. he turn to his girl and say: \"baby, i love you very much\" \"what is it honey?\" \"our car is broken down. i think the engine is broken, ill walk and get some more fuel.\" \"ok. ill stay here and look after our stereo. there have been news report of steres being stolen.\" \"good idea. keep the doors locked no matter what. i love you sweaty\"\n" +
                    "\n" +
                    "so the guy left to get full for the car. after two hours the girl say \"where is my baby, he was supposed to be back by now\". then the girl here a scratching sound and a voice say \"LET ME IN\"\n" +
                    "\n" +
                    "the girl doesn't do it and then after a while she goes to sleep. the next morning she wakes up and finds her boyfriend still not there. she gets out to check and man door hand hook car door.",
            color = Color.White,
            fontSize = 15.sp
        )
    }
}