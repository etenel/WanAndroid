package com.wls.poke.base

import androidx.compose.runtime.mutableStateMapOf
import com.wls.base.BaseAppViewModel

/**
 * app全局viewModel传递消息
 */
class AppViewModel: BaseAppViewModel() {
    val articleCollectList= mutableStateMapOf<Int,Boolean>()


}