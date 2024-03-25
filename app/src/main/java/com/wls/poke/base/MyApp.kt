package com.wls.poke.base

import com.wls.base.BaseApp
import com.wls.base.utils.LogUtils
import com.wls.poke.BuildConfig
import dagger.hilt.android.HiltAndroidApp


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