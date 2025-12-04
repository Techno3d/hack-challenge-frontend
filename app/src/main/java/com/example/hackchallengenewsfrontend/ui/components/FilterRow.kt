package com.example.hackchallengenewsfrontend.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FilterRow(
    filters: List<String>,
    currentFiltersSelected: List<String>,
    onFilterClicked: (String) -> Unit
){
    LazyRow(horizontalArrangement = Arrangement.spacedBy(5.dp)){
        items(filters){
            filter -> FilterButton(text = filter,
            selected = currentFiltersSelected.contains(filter),
            onFilterClicked = { onFilterClicked(filter) })
        }
    }
}

@Composable
fun FilterButton(
    text: String,
    selected: Boolean,
    onFilterClicked: () -> Unit
){
    Button(onClick = onFilterClicked, colors =
    if(selected) {
        ButtonDefaults.buttonColors(Color.White)
    }
    else {
        ButtonDefaults.buttonColors(Color.LightGray)
    }
    ){
        Text(text = text,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight(500),
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun FilterRowPreview(){
    val filters = listOf("Filter 1", "Filter 2", "Filter 3", "Filter 4", "Filter 5")
    val currentFiltersSelected = listOf("Filter 1", "Filter 3")
    FilterRow(filters = filters, currentFiltersSelected = currentFiltersSelected, onFilterClicked = {})
}

@Preview(showBackground = true)
@Composable
fun FilterButtonPreview(){
    FilterButton(text = "Filter 1", selected = true, onFilterClicked = {})
}
