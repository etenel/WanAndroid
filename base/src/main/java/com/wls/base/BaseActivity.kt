package com.wls.base

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import com.wls.base.utils.LogUtils
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


open class BaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startObserver()
    }

    /** 全局错误处理 */
    protected open fun startObserver() {
        BaseApp.baseAppViewModel.apply {
            viewModelScope.launch {
                exception.flowWithLifecycle(lifecycle).collect { e ->
                    LogUtils.e("网络请求错误：${e.message}")
                    when (e) {
                        is SocketTimeoutException -> {
                            Toast.makeText(
                                this@BaseActivity,
                                R.string.request_time_out,
                                Toast.LENGTH_SHORT
                            )
                                .show()

                        }

                        is ConnectException, is UnknownHostException -> {
                            Toast.makeText(
                                this@BaseActivity,
                                R.string.network_error,
                                Toast.LENGTH_SHORT
                            )
                                .show()

                        }

                        else -> {
                            Toast.makeText(this@BaseActivity, e.message, Toast.LENGTH_SHORT)
                                .show()

                        }
                    }
                }
            }

            viewModelScope.launch {
                errorResponse.flowWithLifecycle(lifecycle).collect{ response ->
                    response?.let { baseData ->
                        if (baseData.errorCode == -1001) {
                            Toast.makeText(this@BaseActivity, baseData.errorMsg, Toast.LENGTH_SHORT).show()

                        }
                    }

                }
            }
        }
    }
}