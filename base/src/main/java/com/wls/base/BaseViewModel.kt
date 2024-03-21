package com.wls.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wls.base.entity.ResultState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class BaseViewModel<T> : ViewModel() {
    /** ui状态 */

    private val _uiState = MutableSharedFlow<ResultState<T>>()
    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000), ResultState.None
    )

    protected fun emitState(state: ResultState<T>) {
        launch {
            _uiState.emit(state)
        }
    }



    fun launch(
//        catchBlock: suspend CoroutineScope.() -> Unit = {},
//        finallyBlock: suspend CoroutineScope.() -> Unit = {},
        tryBlock: suspend CoroutineScope.() -> Unit,
    ) {
        // 默认是执行在主线程，相当于launch(Dispatchers.Main)
        viewModelScope.launch(GlobalCoroutineExceptionHandler) {
            tryBlock()
        }
//        viewModelScope.launch {
//            try {
//                tryBlock()
//            } catch (e: Exception) {
//                BaseApp.baseAppViewModel.emitException(e)
//                catchBlock()
//            } finally {
//                finallyBlock()
//            }
//        }
    }
}