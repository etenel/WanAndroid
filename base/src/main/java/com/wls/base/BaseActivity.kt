package com.wls.base

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.net.ParseException
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException


abstract class BaseActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startObserver()
    }

    /** 全局错误处理 */
    protected open fun startObserver() {
        BaseApp.baseAppViewModel.apply {
            viewModelScope.launch {
                exception.flowWithLifecycle(lifecycle).collect { exception ->
                    //这里不光只能打印错误, 还可以根据不同的错误做出不同的逻辑处理
                    //这里只是对几个常用错误进行简单的处理, 展示这个类的用法, 在实际开发中请您自行对更多错误进行更严谨的处理
                    var msg = getString(R.string.unknow_error)
                    if (!TextUtils.isEmpty(exception.message)) {
                        msg = exception.message.orEmpty()
                    }
                    when (exception) {
                        is SocketTimeoutException -> {
                            msg = getString(R.string.net_connect_timeout)
                        }

                        is HttpException -> {
                            msg = GlobalCoroutineExceptionHandler.convertStatusCode(exception)
                        }

                        is ParseException, is JSONException -> {
                            msg = getString(R.string.data_error)
                        }

                        is ConnectException -> {
                            msg = getString(R.string.connect_fail)
                        }

                        is NullPointerException -> {
                            msg = getString(R.string.null_exception)
                        }
                    }
                    if (!TextUtils.isEmpty(msg)) {
                        Toast.makeText(this@BaseActivity, msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }


            viewModelScope.launch {
                errorResponse.flowWithLifecycle(lifecycle).collect{
                    response->
                    response?.let {
                        Toast.makeText(this@BaseActivity,it.errorMsg,Toast.LENGTH_SHORT).show()
                        if (it.errorCode==-1001){
                            login()
                        }
                    }
                }
            }
        }
    }

    abstract fun login()


}