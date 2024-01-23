package com.plutoapps.qotes.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.plutoapps.qotes.R
import com.plutoapps.qotes.data.models.Qote
import com.plutoapps.qotes.ui.composables.QoteText

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val viewModel = ViewModelProvider(LocalContext.current as ViewModelStoreOwner)[HomeViewModel::class.java]
    val homeUiState = viewModel.homeState.collectAsState()

    val getQote : () -> Unit = {
        viewModel.getQote()
    }

    LaunchedEffect(Unit){
        if(viewModel.homeState.value?.qote == null){
            getQote()
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(homeUiState.value!!.isLoading){
                CircularProgressIndicator()
            } else {
                if(homeUiState.value!!.qote == null){
                    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(stringResource(R.string.an_error_occurred), textAlign = TextAlign.Center, style = MaterialTheme.typography.labelLarge)
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
                        qote =homeUiState.value!!.qote!!
                    )
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
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}