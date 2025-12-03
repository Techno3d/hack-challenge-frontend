package com.example.hackchallengenewsfrontend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun NewsCard(
    newsSource: String,
    title: String,
    thumbnailUrl: String,
    thumbnailDescription: String? = null,
    onCardClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.background(Color.LightGray)
            .clickable { onCardClick() }
    ) {
        AsyncImage(
            model = thumbnailUrl,
            contentDescription = thumbnailDescription,
//            placeholder = ,
            modifier = Modifier.width(300.dp).height(200.dp).padding(horizontal = 2.dp)
        )
        Spacer(modifier = Modifier.height(5.dp).fillMaxWidth().background(Color.Gray))
        Text(newsSource, fontSize = 15.sp, textAlign = TextAlign.Left, modifier = Modifier.padding(5.dp))
        Spacer(modifier = Modifier.height(12.dp))
        Text(title, fontSize = 20.sp, textAlign = TextAlign.Left, modifier = Modifier.padding(5.dp))

    }
}

@Preview
@Composable
fun NewsCardPreview() {
    NewsCard(newsSource = "Cornell Chronicle",
        title = "Winter storm snarls flights for post-Thanksgiving travelers in Chicago",
        thumbnailUrl = "https://d3i6fh83elv35t.cloudfront.net/static/2025/11/GettyImages-2248617554-1200x800.jpg",
        thumbnailDescription = "Winter Storm Snarls Air Travel In Chicago",
        onCardClick = {})
}