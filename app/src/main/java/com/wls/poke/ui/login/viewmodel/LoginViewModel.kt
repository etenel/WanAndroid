package com.wls.poke.ui.login.viewmodel

import android.widget.Toast
import com.wls.base.BaseApp
import com.wls.base.BaseViewModel
import com.wls.base.entity.result
import com.wls.poke.entity.UserEntity
import com.wls.poke.ui.login.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) :
    BaseViewModel<UserEntity>() {
    fun login(account: String, password: String) {
        if (account.isEmpty() or password.isEmpty()) {
            Toast.makeText(BaseApp.appContext,"账号和密码不能为空",Toast.LENGTH_SHORT).show()
            return
        }
        launch {
            repository.login(account, password).result {
            }.collect {
                emitState(it)
            }
        }
    }
}