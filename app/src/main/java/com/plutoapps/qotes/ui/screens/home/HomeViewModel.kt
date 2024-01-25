package com.plutoapps.qotes.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.plutoapps.qotes.data.models.Qote
import com.plutoapps.qotes.data.repositories.QoteApi
import com.plutoapps.qotes.data.repositories.QotesRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Calendar
import java.util.UUID

class HomeViewModel(private val qotesRepo: QotesRepo) : ViewModel() {

    private var _homeState = MutableStateFlow(HomeUi())
    val homeState: StateFlow<HomeUi?> = _homeState

    val favoritedQotes  : StateFlow<List<Qote>> = qotesRepo.getAllQotes().map { it }.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000L), initialValue = emptyList())

    fun getQote() {
        viewModelScope.launch {
            try {
                _homeState.update {
                    it.copy(isLoading = true)
                }
                val result = QoteApi.retrofitService.getQote("happiness")
                _homeState.update {
                    it.copy(
                        qote = result.first().copy(
                            id = UUID.randomUUID().toString(),
                            date = Calendar.getInstance().timeInMillis.toString()
                        ), isLoading = false, currentFavorite = null
                    )
                }
            } catch (e: Exception) {
                _homeState.update {
                    it.copy(isLoading = false, qote = null)
                }
            }
        }

    }

    fun setCurrentTab(tabIndex: Int) {
        _homeState.update {
            it.copy(currentTab = tabIndex)
        }
    }

    fun setCurrentFavorite(id: String?) {
        _homeState.update {
            it.copy(currentFavorite = id)
        }
    }

    fun favoriteAQote(qote: Qote){
        viewModelScope.launch {
            qotesRepo.favoriteQote(qote)
            _homeState.update { it.copy(currentFavorite = qote.id) }
        }
    }

    fun unFavoriteAQote(qote: Qote){
        viewModelScope.launch {
            qotesRepo.unFavoriteQote(qote)
            _homeState.update { it.copy(currentFavorite = null) }
        }
    }

}

class HomeViewModelFactory(private val qotesRepo: QotesRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(qotesRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}