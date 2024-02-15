package com.plutoapps.qotes.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.plutoapps.qotes.QotesApplication
import com.plutoapps.qotes.R
import com.plutoapps.qotes.data.models.Qote

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    //val viewModel =
    //    ViewModelProvider(LocalContext.current as ViewModelStoreOwner)[HomeViewModel::class.java]
    val viewModel = HomeViewModelFactory(QotesApplication.qotesRepo!!,QotesApplication.userPreferencesRepository!!).create(HomeViewModel::class.java)
    val homeUiState = viewModel.homeState.collectAsState()
    val favourites = viewModel.favoritedQotes.collectAsState()

    val navbarItems = listOf("Qotes" to R.drawable.quote, "Favourites" to R.drawable.favorite,"Settings" to R.drawable.settings)

    val selectTab: (Int) -> Unit = {
        viewModel.setCurrentTab(it)
    }

    val getQote: () -> Unit = {
        viewModel.initialLoad()
    }

    val getNewQote: () -> Unit = {
        viewModel.getQote()
    }

    val deleteFavouritedQote : (Qote) -> Unit = {
        viewModel.unFavoriteAQote(it)
    }

    val toggleFavorite: (qote: Qote,shouldUnFavorite:Boolean) -> Unit = {
        qote,shouldUnFavorite ->
        if(shouldUnFavorite){
            viewModel.unFavoriteAQote(qote)
        } else {
            viewModel.favoriteAQote(qote)
        }
    }

    val setReminder : (Long?) -> Unit = {
        viewModel.setReminder(it)
    }

    LaunchedEffect(Unit) {
        if (viewModel.homeState.value?.qote == null) {
            getQote()
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                navbarItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(painterResource(id = item.second), contentDescription = null) },
                        label = { Text(item.first) },
                        selected = homeUiState.value?.currentTab == index,
                        onClick = { selectTab(index) }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (homeUiState.value!!.currentTab == 0)
                QoteTab(homeUiState = homeUiState.value!!, getQote = getQote,getNewQote = getNewQote, toggleFavorite = toggleFavorite)
            else if(homeUiState.value!!.currentTab == 1)
                FavoritesTab(favourites = favourites.value, deleteFavouritedQote = deleteFavouritedQote)
            else
                SettingsTab(reminderTime = homeUiState.value?.reminderTime,setReminder = setReminder)
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}