package com.plutoapps.qotes.ui.screens.home

import com.plutoapps.qotes.data.models.Qote

data class HomeUi(
    val qote: Qote? = null,
    val isLoading: Boolean = true,
    val currentTab: Int = 0,
    val favourites: List<Qote> = emptyList(),
    val currentFavorite: String? = null
)

