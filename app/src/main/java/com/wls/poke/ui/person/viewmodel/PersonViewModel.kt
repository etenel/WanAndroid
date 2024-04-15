package com.wls.poke.ui.person.viewmodel

import com.wls.base.BaseViewModel
import com.wls.base.entity.result
import com.wls.poke.ui.person.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(private val repository: PersonRepository) :
    BaseViewModel<Unit>() {
    val login = false

    init {
        if (login) {
            userInfo()
        }
    }

    private fun userInfo() {
        launch {
            repository.userInfo().result {

            }.collect()
        }
    }
}
