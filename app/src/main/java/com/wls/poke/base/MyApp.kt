package com.wls.poke.base

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.wls.base.BaseApp
import com.wls.base.utils.LogUtils
import com.wls.poke.BuildConfig
import com.wls.poke.entity.UserEntity
import dagger.hilt.android.HiltAndroidApp

val Context.myDataStore by preferencesDataStore("user")
var userInfoEntity: UserEntity?=null
@HiltAndroidApp
class MyApp: BaseApp() {
    companion object {
        lateinit var appViewModel: AppViewModel
    }

    override fun onCreate() {
        super.onCreate()
        appViewModel = getAppViewModelProvider()[AppViewModel::class.java]
        LogUtils.config.setLogSwitch(BuildConfig.LOG_ENABLE)
            .setConsoleSwitch(BuildConfig.LOG_ENABLE)

    }
}