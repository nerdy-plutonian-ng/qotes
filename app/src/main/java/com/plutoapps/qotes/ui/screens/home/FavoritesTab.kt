package com.plutoapps.qotes.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plutoapps.qotes.R
import com.plutoapps.qotes.data.models.Qote
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesTab(modifier: Modifier = Modifier, favourites: List<Qote>, deleteFavouritedQote : (Qote) -> Unit) {

    val dateFormat = SimpleDateFormat("EEEE dd MMM yyyy", Locale.getDefault())
    val scope = rememberCoroutineScope()

    if(favourites.isEmpty())
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){

            Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.padding(16.dp)){
                Icon(painter = painterResource(id = R.drawable.empty_folder), contentDescription = null, modifier = modifier.size(64.dp))
                Text(text = "Your favorites are empty, once you add some you will see them here.", style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center)
            }
        }
        else
        LazyColumn {
        items(favourites.size, key = { favourites[it].id }) {
            val qote = favourites[it]
            val dismissState = rememberDismissState(
                initialValue = DismissValue.Default,
                positionalThreshold = { a -> a / 3 },
                confirmValueChange = {dismissValue ->
                    dismissValue.name
                    true }
            )
            SwipeToDismiss(state = dismissState, background = {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.errorContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Button(onClick = { deleteFavouritedQote(qote) }) {
                            Icon(Icons.Default.Delete,null)
                            Spacer(modifier = modifier.width(16.dp))
                            Text(stringResource(R.string.delete))
                        }
                        OutlinedButton(onClick = {
                            scope.launch {
                                dismissState.reset()
                            }
                        }) {
                            Icon(Icons.Default.Close,null)
                            Spacer(modifier = modifier.width(16.dp))
                            Text(stringResource(R.string.cancel))
                        }
                    }
                }
            },
                directions = setOf(DismissDirection.EndToStart),
                dismissContent = {
                    Column {
                        ListItem(
                            headlineContent = { Text(qote.quote) },
                            overlineContent = { Text(dateFormat.format(Date(qote.date.toLong()))) },
                            supportingContent = { Text("- ${qote.author}") },
                            leadingContent = {
                                Icon(
                                    painterResource(id = R.drawable.quote),
                                    contentDescription = null,
                                )
                            },
                        )
                        Divider()
                    }
                })
        }
    }
}

@Preview
@Composable
fun FavoritesTabPreview() {
    FavoritesTab(favourites = emptyList(), deleteFavouritedQote = {})
}