package com.wls.poke

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.wls.base.BaseApp
import com.wls.base.BaseViewModel
import com.wls.base.entity.ResultState
import com.wls.poke.base.myDataStore
import com.wls.poke.base.userInfoEntity
import com.wls.poke.entity.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : BaseViewModel<Unit>() {


    init {

        launch {
            BaseApp.appContext.myDataStore.edit {
                val user = it[stringPreferencesKey("user")]
                if (!user.isNullOrEmpty()){
                    userInfoEntity =
                        Json.Default.decodeFromString<UserEntity>(user)
                }

            }
            withContext(Dispatchers.IO) {
                emitState(ResultState.Loading)
                delay(1500)
                emitState(ResultState.None)
            }

        }
    }

}