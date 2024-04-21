@file:OptIn(ExperimentalCoroutinesApi::class)

package com.wls.poke.ui.login.viewmodel

import android.widget.Toast
import com.wls.base.BaseApp
import com.wls.base.BaseViewModel
import com.wls.base.entity.result
import com.wls.poke.entity.UserEntity
import com.wls.poke.ui.login.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) :
    BaseViewModel<UserEntity>() {
    val login = MutableSharedFlow<(String, String) -> Unit>()


    fun login(account: String, password: String) {
        if (account.isEmpty() or password.isEmpty()) {
            Toast.makeText(BaseApp.appContext, "账号和密码不能为空", Toast.LENGTH_SHORT).show()
            return
        }
        launch {
            repository.login(account, password).result {
            }.onEach {
                emitState(it)
            }.collect()
        }
    }

}