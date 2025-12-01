package com.example.hackchallengenewsfrontend.components

import androidx.compose.foundation.background
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
fun newsCard(
    title: String,
    thumbnail_url: String,
    thumbnail_alt_text: String? = null,
) {
    Column(
        modifier = Modifier.background(Color.LightGray)
    ) {
        AsyncImage(
            model = thumbnail_url,
            contentDescription = thumbnail_alt_text,
//            placeholder = ,
            modifier = Modifier.width(300.dp).height(200.dp).padding(horizontal = 2.dp)
        )
        Spacer(modifier = Modifier.height(5.dp).fillMaxWidth().background(Color.Gray))
        Text(title, fontSize = 20.sp, textAlign = TextAlign.Left, modifier = Modifier.padding(5.dp))
    }
}

@Preview
@Composable
fun newsCardPreview() {
    newsCard("Winter storm snarls flights for post-Thanksgiving travelers in Chicago", "https://d3i6fh83elv35t.cloudfront.net/static/2025/11/GettyImages-2248617554-1200x800.jpg", "Winter Storm Snarls Air Travel In Chicago")
}