package com.plutoapps.qotes.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.plutoapps.qotes.R
import com.plutoapps.qotes.data.models.Qote
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun FavoritesTab(modifier: Modifier = Modifier, favourites: List<Qote>) {

    val dateFormat = SimpleDateFormat("EEEE dd MMM yyyy", Locale.getDefault())

    LazyColumn{
        items(favourites.size,key = {favourites[it].id}){
            //ListItem(headlineContent = { Text(favourites[it].quote) },)
            Column {
                ListItem(
                    headlineContent = { Text(favourites[it].quote) },
                    overlineContent = { Text(dateFormat.format(Date(favourites[it].date.toLong()))) },
                    supportingContent = { Text("- ${favourites[it].author}") },
                    leadingContent = {
                        Icon(
                            painterResource(id = R.drawable.quote),
                            contentDescription = null,
                        )
                    },
                )
                Divider()
            }
        }
    }
}

@Preview
@Composable
fun FavoritesTabPreview() {
    FavoritesTab(favourites = emptyList())
}