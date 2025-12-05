package com.example.hackchallengenewsfrontend.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth

@Composable
fun NewsCard(
    title: String,
    thumbnailUrl: String,
    thumbnailDescription: String? = null,
    author: String,
    newsSource: String,
    date: String,
    modifier : Modifier = Modifier,
    onCardClick: () -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .background(Color.Black)
            .clickable { onCardClick() }
    ) {
        AsyncImage(
            model = thumbnailUrl,
            contentDescription = thumbnailDescription,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f / 3f)
        )
        Column(modifier = Modifier
            .padding(12.dp)
        ) {
            Text(newsSource, fontSize = 15.sp, textAlign = TextAlign.Left, color = Color.White)
            Text(title, fontSize = 20.sp, textAlign = TextAlign.Left, color = Color.White)
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(date, fontSize = 12.sp, color = Color.Gray)
                Text(author, fontSize = 12.sp, color = Color.Gray)
            }
        }

    }
}

@Preview
@Composable
fun NewsCardPreview() {
    NewsCard(newsSource = "Cornell Chronicle",
        title = "Winter storm snarls flights for post-Thanksgiving travelers in Chicago",
        thumbnailUrl = "https://d3i6fh83elv35t.cloudfront.net/static/2025/11/GettyImages-2248617554-1200x800.jpg",
        author = "Hello",
        thumbnailDescription = "Winter Storm Snarls Air Travel In Chicago",
        date = "19/2/22",
        onCardClick = {})
}