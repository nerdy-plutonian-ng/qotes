package com.plutoapps.qotes.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plutoapps.qotes.R
import com.plutoapps.qotes.data.models.Qote
import com.plutoapps.qotes.ui.composables.QoteText

@Composable
fun QoteTab(
    modifier: Modifier = Modifier,
    homeUiState: HomeUi,
    getQote: () -> Unit,
    toggleFavorite: (Qote,Boolean) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (homeUiState.isLoading) {
            CircularProgressIndicator()
        } else {
            if (homeUiState.qote == null) {
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        stringResource(R.string.an_error_occurred),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Button(onClick = getQote) {
                        Icon(
                            painter = painterResource(id = R.drawable.refresh),
                            contentDescription = null
                        )
                        Spacer(modifier = modifier.width(16.dp))
                        Text(text = stringResource(R.string.try_again))
                    }
                }
            } else {
                QoteText(
                    qote = homeUiState.qote
                )
                IconButton(onClick = {
                    toggleFavorite(homeUiState.qote,homeUiState.currentFavorite == homeUiState.qote.id)
                }) {
                    Icon(
                        Icons.Default.Favorite,
                        null,
                        tint = if (homeUiState.currentFavorite == homeUiState.qote.id) Color.Red else Color.Gray,
                        modifier = modifier.size(64.dp)
                    )
                }
                Button(onClick = getQote) {
                    Icon(
                        painter = painterResource(id = R.drawable.refresh),
                        contentDescription = null
                    )
                    Spacer(modifier = modifier.width(16.dp))
                    Text(text = stringResource(R.string.another))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QoteTabPreview() {
    QoteTab(homeUiState = HomeUi(), getQote = {}, toggleFavorite = { _, _ -> })
}

