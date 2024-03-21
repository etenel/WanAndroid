package com.wls.poke.ui.login.viewmodel

import com.wls.base.BaseViewModel
import com.wls.base.entity.Null
import com.wls.base.entity.ResultState
import com.wls.base.entity.result
import com.wls.poke.ui.login.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : BaseViewModel<Null>() {
    init {
        launch {
            delay(2000)
            emitState(ResultState.Loading)
        }
    }

    fun login(account: String, password: String) {
        launch {
            repository.login(account, password).result {

            }.collect{emitState(it)}
        }
    }
}