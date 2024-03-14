package com.wls.poke

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {

    val uiState = flowOf(UIState.Loading)
        .map {
            delay(1000)
            UIState.DismissDialogEvent
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            UIState.Loading
        )

    sealed interface UIState {
        data object DismissDialogEvent : UIState
        data object Loading : UIState
    }
}