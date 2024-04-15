package com.wls.base.entity

import com.wls.base.BaseApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


sealed interface ResultState<out T> {
    data class Success<T>(val data: T?) : ResultState<T>
    data class Error(val exception: Throwable) : ResultState<Nothing>
    data object Loading : ResultState<Nothing>
    data object None : ResultState<Nothing>
}

/**
 * @param onError 请求失败回调，默认false统一处理
 * @param onSuccess 请求成功回调
 */
suspend fun <T> Flow<BaseData<T>>.result(
    onError: suspend (BaseData<T>) -> Boolean = { false },
    onSuccess: suspend (T?) -> Unit = {},
): Flow<ResultState<T>> {
    return this.map<BaseData<T>, ResultState<T>> {
        when (it.errorCode) {
            0 -> {
                onSuccess(it.data)
                ResultState.Success(it.data)
            }
            else -> {
                if (!onError(it)) {
                    BaseApp.baseAppViewModel.emitErrorResponse(it)
                }
                ResultState.Error(Exception(it.errorMsg))
            }
        }
    }.onStart {
        emit(ResultState.Loading)
    }.catch {
       // BaseApp.baseAppViewModel.emitException(it)
        ResultState.Error(it)
    }


}

