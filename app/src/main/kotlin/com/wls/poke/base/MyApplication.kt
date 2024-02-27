package com.wls.poke.base

import android.app.Application
import android.content.Context
import com.wls.poke.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import kotlin.properties.Delegates

@HiltAndroidApp
class MyApplication:Application() {
    companion object {
        var CONTEXT: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        LogUtils.config.setLogSwitch(BuildConfig.LOG_ENABLE)
            .setConsoleSwitch(BuildConfig.LOG_ENABLE)

    }
}