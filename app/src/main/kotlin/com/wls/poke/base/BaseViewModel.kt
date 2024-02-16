package com.wls.poke.base

import android.content.Intent
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class BaseViewModel:ViewModel() {
    private val uiState = Channel<UIState>()
    val uiStates get() = uiState.receiveAsFlow()
    sealed interface UIState {
        data class ShowDialogEvent<T>(val data: T) : UIState
        data object DismissDialogEvent : UIState
        data object FinishEvent : UIState
        data class FinishResultEvent(val resultCode: Int, val intent: Intent) : UIState
        data object OnBackPressedEvent : UIState
        data class ShowToastEvent(val msg: String = "") : UIState
        data object FinishRefreshEvent : UIState
        data object FinishLoadMoreEvent : UIState
    }
}