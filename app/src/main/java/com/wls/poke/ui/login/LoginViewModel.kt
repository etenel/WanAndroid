package com.wls.poke.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wls.poke.base.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableSharedFlow<ResultState<Any>>()
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000), ResultState.None
    )
init {
    viewModelScope.launch {
        delay(2000)
        _state.emit(ResultState.Loading)
    }
}
    fun login(account: String, password: String) {
        viewModelScope.launch {
            _state.emit(ResultState.UnLogin())
        }
    }


}