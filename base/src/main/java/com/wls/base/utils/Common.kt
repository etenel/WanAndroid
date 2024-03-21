package com.wls.base.utils

import android.view.Window
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows

/**
 *  设置状态栏是否透明，即是否取消沉浸式状态栏
 *  @param fits true表示取消沉浸式状态栏，即状态栏为透明色
 */
fun decorFitsSystemWindows(fits: Boolean,window: Window) {
        setDecorFitsSystemWindows(window, false)

}

