package com.wls.poke.entity

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


    sealed interface ResultState<out T> {
        data class Success<T>(val data: T) : ResultState<T>
        data class Error(val exception: Throwable? = null) : ResultState<Nothing>
        data class Empty(val msg: String = "") : ResultState<Nothing>
        data object Loading : ResultState<Nothing>
        data class UnLogin(val msg: String = "请先登录账户") :ResultState<Nothing>

    }

    fun <T> Flow<BaseData<T>>.asResult(): Flow<ResultState<T>> {
        return this.map<BaseData<T>, ResultState<T>> {
            when (it.errorCode) {
                0 -> {
                    if (it.data != null) {
                        ResultState.Success(it.data)
                    } else {
                        ResultState.Empty(it.errorMsg)
                    }
                }
                -1001 -> ResultState.UnLogin()
                else -> ResultState.Empty(it.errorMsg)
            }
        }
            .onStart { emit(ResultState.Loading) }
            .catch {
                emit(ResultState.Error(it))
            }

    }
