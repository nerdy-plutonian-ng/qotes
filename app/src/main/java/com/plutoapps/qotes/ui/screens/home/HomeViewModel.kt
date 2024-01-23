package com.plutoapps.qotes.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plutoapps.qotes.data.repositories.QoteApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel : ViewModel() {

    private var _homeState = MutableStateFlow(HomeUi())

    val homeState: StateFlow<HomeUi?> = _homeState

    fun getQote() {
        viewModelScope.launch {
            try {
                _homeState.update {
                    it.copy(isLoading = true)
                }
                val result = QoteApi.retrofitService.getQote("happiness")
                _homeState.update {
                    it.copy(qote = result.first(), isLoading = false)
                }
            } catch (e: Exception) {
                _homeState.update {
                    it.copy(isLoading = false, qote = null)
                }
            }
        }

    }

}