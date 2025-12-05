package com.example.hackchallengenewsfrontend.screens

import android.R.attr.author
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hackchallengenewsfrontend.ui.components.CompactNewsCard
import com.example.hackchallengenewsfrontend.ui.components.FilterRow
import com.example.hackchallengenewsfrontend.ui.components.NewsCard
import com.example.hackchallengenewsfrontend.viewmodels.NewsViewModel

@Composable
fun MainListenScreen(
    viewArticle: (String) -> Unit
) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(top = 30.dp, start = 22.dp, end = 22.dp)
    ) {
        // Header
        item {
            Text(
                "Scope",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                fontSize = 40.sp,
                textAlign = TextAlign.Left,
                color = Color.White
            )
            Text(
                "Audio",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                fontSize = 20.sp,
                textAlign = TextAlign.Left,
                color = Color.LightGray
            )
            Spacer(Modifier.height(24.dp))
            Text(
                "Editor's Picks",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                fontSize = 20.sp,
                textAlign = TextAlign.Left,
                color = Color.White
            )
            Spacer(Modifier.height(12.dp))
        }

        // Body
        //TODO Change NewsCard/CompactNewsCard to be not hard-coded
        item {
            Row(modifier = Modifier
                .horizontalScroll(state = rememberScrollState())
                .fillMaxWidth()){
                NewsCard(
                    newsSource = "Cornell Chronicle",
                    title = "Winter storm snarls flights for post-Thanksgiving travelers in Chicago",
                    thumbnailUrl = "https://d3i6fh83elv35t.cloudfront.net/static/2025/11/GettyImages-2248617554-1200x800.jpg",
                    thumbnailDescription = "Winter Storm Snarls Air Travel In Chicago",
                    onCardClick = { viewArticle("https://www.pbs.org/newshour/nation/winter-storm-snarls-flights-for-post-thanksgiving-travelers-in-chicago") },
                    author = "Author",
                    date = "Date",
                    modifier = Modifier.width(300.dp)
                )

                Spacer(modifier = Modifier.width(20.dp))

                NewsCard(
                    newsSource = "Cornell Chronicle",
                    title = "Winter storm snarls flights for post-Thanksgiving travelers in Chicago",
                    thumbnailUrl = "https://d3i6fh83elv35t.cloudfront.net/static/2025/11/GettyImages-2248617554-1200x800.jpg",
                    thumbnailDescription = "Winter Storm Snarls Air Travel In Chicago",
                    onCardClick = { viewArticle("https://www.pbs.org/newshour/nation/winter-storm-snarls-flights-for-post-thanksgiving-travelers-in-chicago") },
                    author = "Author",
                    date = "Date",
                    modifier = Modifier.width(300.dp)
                )

                Spacer(modifier = Modifier.width(20.dp))

                NewsCard(
                    newsSource = "Cornell Chronicle",
                    title = "Winter storm snarls flights for post-Thanksgiving travelers in Chicago",
                    thumbnailUrl = "https://d3i6fh83elv35t.cloudfront.net/static/2025/11/GettyImages-2248617554-1200x800.jpg",
                    thumbnailDescription = "Winter Storm Snarls Air Travel In Chicago",
                    onCardClick = { viewArticle("https://www.pbs.org/newshour/nation/winter-storm-snarls-flights-for-post-thanksgiving-travelers-in-chicago") },
                    author = "Author",
                    date = "Date",
                    modifier = Modifier.width(300.dp)
                )

            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Text(
                "Audio Stories For You",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                fontSize = 20.sp,
                textAlign = TextAlign.Left,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            CompactNewsCard(
                title = "TEMP TITLE",
                newsSource = "TEMP SOURCE",
                author = "TEMP AUTHOR",
                thumbnailUrl = "TEMP URL",
                thumbnailDescription = "TEMP DESC",
                onCardClick = {},
                date = "TEMP DATE"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainListenScreenPreview(){
    MainListenScreen(viewArticle = {})
}