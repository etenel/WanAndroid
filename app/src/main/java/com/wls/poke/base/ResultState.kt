package com.wls.poke.base

import com.wls.base.entity.BaseData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


sealed interface ResultState<out T> {
    data class Success<T>(val data: T) : ResultState<T>
    data class Error(val exception: Throwable) : ResultState<Nothing>
    data object Loading : ResultState<Nothing>
    data object None: ResultState<Nothing>
    data class Empty(val msg: String) : ResultState<Nothing>
    data class UnLogin(val msg: String = "请先登录账户") : ResultState<Nothing>

}

const val noMoreData = "没有更多数据"
fun <T> Flow<BaseData<T>>.result(
    onEmpty: (String) -> Unit = {},
    onError: (BaseData<T>) -> Unit = {},
    onSuccess: (T) -> Unit,
): Flow<ResultState<T>> {
    return this.map<BaseData<T>, ResultState<T>> {
        when(it.errorCode){
            0-> {
                if (it.data != null) {
                    onSuccess(it.data)
                    ResultState.Success(it.data)
                } else {
                    MyApp.appViewModel.emitException(Throwable(it.errorMsg.ifEmpty { noMoreData }))
                    onEmpty(it.errorMsg.ifEmpty { noMoreData })
                    ResultState.Empty(it.errorMsg.ifEmpty { noMoreData })
                }
            }
            -1001-> {
                MyApp.appViewModel.emitErrorResponse(it)
                onError(it)
                ResultState.UnLogin()
            }
            else->{
                MyApp.appViewModel.emitErrorResponse(it)
                onError(it)
                ResultState.Error(Throwable(it.errorMsg))
            }
        }
    }.onStart {
        emit(ResultState.Loading)
    }.catch {
        MyApp.appViewModel.emitException(it)
        emit(ResultState.Error(it))
    }

}

