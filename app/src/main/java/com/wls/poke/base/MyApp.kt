package com.wls.poke.base

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.gms.net.CronetProviderInstaller
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
        CronetProviderInstaller.installProvider(appContext).addOnCompleteListener {
            if (it.isSuccessful) {
                LogUtils.i( "Successfully installed Play Services provider: $it")
            } else {
                LogUtils.w( "Unable to load Cronet from Play Services", it.exception.toString())
            }
        }
    }
}