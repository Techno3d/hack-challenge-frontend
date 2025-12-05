package com.example.hackchallengenewsfrontend.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hackchallengenewsfrontend.R
import com.example.hackchallengenewsfrontend.ui.components.CompactNewsCard
import com.example.hackchallengenewsfrontend.ui.components.FilterRow
import com.example.hackchallengenewsfrontend.ui.theme.Secondary

@Composable
fun SavedScreen(){

    //Use viewmodel data
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black)
        .padding(top=30.dp, start = 22.dp, end = 22.dp)
    ){
        item {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text(
                        "Scope",
                        modifier = Modifier.padding(vertical = 2.dp),
                        fontSize = 36.sp,
                        textAlign = TextAlign.Left,
                        fontStyle = FontStyle.Italic,
                        color = Color.White
                    )
                    Text(
                        "Home",
                        modifier = Modifier.padding(vertical = 2.dp),
                        fontSize = 36.sp,
                        textAlign = TextAlign.Left,
                        color = Secondary
                    )
                }
                IconButton(onClick = {/* TODO: Search Functionality */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_searchbutton),
                        contentDescription = "Search Button",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
            Spacer(Modifier.height(30.dp))
        }
        items(listOf<String>("art 1", "art 2")){ article ->
            CompactNewsCard(
                title = article,
                newsSource = "TEMP SOURCE",
                author = "TEMP AUTHOR",
                thumbnailUrl = "TEMP URL",
                thumbnailDescription = "TEMP DESC",
                onCardClick = {},
                date = "TEMP DATE"
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SavedScreenPreview(){
    SavedScreen()
}