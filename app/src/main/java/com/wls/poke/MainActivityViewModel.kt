package com.wls.poke

import com.wls.base.BaseViewModel
import com.wls.base.entity.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : BaseViewModel<Unit>() {


    init {
        launch {
            withContext(Dispatchers.IO){
                emitState(ResultState.Loading)
                delay(1500)
                emitState(ResultState.None)
            }

        }
    }

}