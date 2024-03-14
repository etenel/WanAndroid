package com.wls.base.utils

import android.os.Build
import android.view.View
import android.view.Window

/**
 *  设置状态栏是否透明，即是否取消沉浸式状态栏
 *  @param fits true表示取消沉浸式状态栏，即状态栏为透明色
 */
fun Window.decorFitsSystemWindows(fits: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        setDecorFitsSystemWindows(fits)
    } else {
        decorView.systemUiVisibility =
            if (fits) View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN else
                View.SYSTEM_UI_FLAG_VISIBLE
    }
}