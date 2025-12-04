package com.example.hackchallengenewsfrontend.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun CompactNewsCard(
    newsSource: String,
    title: String,
    author: String,
    thumbnailUrl: String,
    thumbnailDescription: String,
    date: String,
    onCardClick: () -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth()
            .background(Color.LightGray)
            .border(width = 4.dp, color = Color.Black, shape = RectangleShape)
            .padding(4.dp)
            .clickable { onCardClick() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            model = thumbnailUrl,
            contentDescription = thumbnailDescription,
//            placeholder = ,
            modifier = Modifier.width(115.dp)
                .height(115.dp)
                .padding(13.dp)
        )
        Column(modifier = Modifier.fillMaxWidth()
            .height(115.dp)
            .padding(6.dp),
            verticalArrangement = Arrangement.SpaceBetween){
            Text(text = newsSource, fontSize = 15.sp)
            Text(text = title, fontSize = 18.sp, maxLines = 3, overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Left)
            Text(text = "$date $author", fontSize = 12.sp)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CompactNewsCardPreview(){
    CompactNewsCard(newsSource = "Cornell Chronicle",
        title = "Winter storm snarls flights for post-Thanksgiving travelers in Chicago",
        author = "Goated Author",
        thumbnailUrl = "https://d3i6fh83elv35t.cloudfront.net/static/2025/11/GettyImages-2248617554-1200x800.jpg",
        thumbnailDescription = "Winter Storm Snarls Air Travel In Chicago",
        date = "no",
        onCardClick = {})
}

