package com.example.hackchallengenewsfrontend.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.example.hackchallengenewsfrontend.ui.theme.Articles
import com.example.hackchallengenewsfrontend.ui.theme.Primary
import com.example.hackchallengenewsfrontend.ui.theme.Secondary

@Composable
fun NewsCard(
    title: String,
    thumbnailUrl: String,
    thumbnailDescription: String? = null,
    author: String,
    newsSource: String,
    date: String,
    modifier : Modifier = Modifier,
    isCompact: Boolean = false,
    isAudio: Boolean = false,
    onCardClick: () -> Unit = {},
    onPlayButtonClicked: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(if(isCompact) 4.dp else 8.dp),
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Articles)
            .clickable { onCardClick() }
    ) {
        AsyncImage(
            model = thumbnailUrl,
            contentDescription = thumbnailDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f / 3f)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(if(isCompact) 5.dp else 12.dp),
            modifier = Modifier
                .padding(if(isCompact) 3.dp else 12.dp)
        ) {
            Text(newsSource, fontSize = if(isCompact) 10.sp else 14.sp, textAlign = TextAlign.Left, color = Primary)
            Text(title, fontSize = if(isCompact) 14.sp else 18.sp, textAlign = TextAlign.Left, color = Primary, lineHeight = if(isCompact) 16.sp else 20.sp)
            if(!isAudio) {
                Text("$date $author", fontSize = 12.sp, color = Secondary)
            } else {
                // TODO: Play Widget
            }
        }

    }
}

@Preview(showBackground = true)
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