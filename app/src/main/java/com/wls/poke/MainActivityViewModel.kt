package com.wls.poke

import com.wls.base.BaseViewModel
import com.wls.base.entity.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : BaseViewModel<Unit>() {


    init {
        launch {
            emitState(ResultState.Loading)
            delay(1500)
            emitState(ResultState.None)
        }
    }

}