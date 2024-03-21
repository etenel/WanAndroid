package com.wls.poke.ui.login.viewmodel

import com.wls.base.BaseViewModel
import com.wls.poke.ui.login.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository:RegisterRepository) :
    BaseViewModel<Unit>() {


}