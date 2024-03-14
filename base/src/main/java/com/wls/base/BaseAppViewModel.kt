package com.wls.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wls.base.entity.BaseData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 * APP全局ViewModel
 */
open class BaseAppViewModel : ViewModel() {

    /** 请求异常（服务器请求失败，譬如：服务器连接超时等） */
    private val _exception = MutableSharedFlow<Throwable>()
    val exception: SharedFlow<Throwable> = _exception

    /** 请求服务器返回错误（服务器请求成功但status错误，譬如：登录过期等） */
    private val _errorResponse = MutableSharedFlow<BaseData<*>>()
    val errorResponse: SharedFlow<BaseData<*>?> = _errorResponse

    /** emit请求出错 */
    fun emitException(exception: Throwable) {
        viewModelScope.launch {
            _exception.emit(exception)
        }
    }

    /** emit请求错误信息 */
    fun emitErrorResponse(apiResponse: BaseData<*>) {
        viewModelScope.launch {
            _errorResponse.emit(apiResponse)
        }
    }
}