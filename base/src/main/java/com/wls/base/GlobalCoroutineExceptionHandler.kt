package com.wls.base

import android.text.TextUtils
import android.widget.Toast
import androidx.core.net.ParseException
import com.wls.base.utils.LogUtils
import kotlinx.coroutines.CoroutineExceptionHandler
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.cancellation.CancellationException


object GlobalCoroutineExceptionHandler : CoroutineExceptionHandler {
    override val key: CoroutineContext.Key<*>
        get() = CoroutineExceptionHandler //To change initializer of created properties use File | Settings | File Templates.

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        LogUtils.e(exception.message.orEmpty())
//        if (exception is UndeliverableException) {
//            return
//        }
        if (exception is CancellationException) return
        //这里不光只能打印错误, 还可以根据不同的错误做出不同的逻辑处理
        //这里只是对几个常用错误进行简单的处理, 展示这个类的用法, 在实际开发中请您自行对更多错误进行更严谨的处理
        var msg = "未知错误"
        if (!TextUtils.isEmpty(exception.message)) {
            msg = exception.message.orEmpty()
        }
        when (exception) {
            is SocketTimeoutException -> {
                msg = "请求网络超时"
            }

            is HttpException -> {
                msg = convertStatusCode(exception)
            }

            is ParseException, is JSONException -> {
                msg = "数据解析错误"
            }

            is ConnectException -> {
                msg = "连接服务器失败"
            }

            is NullPointerException -> {
                msg = "空指针异常"
            }
        }
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(BaseApp.appContext, msg, Toast.LENGTH_LONG).show()
        }
    }

     fun convertStatusCode(httpException: HttpException): String {
        val msg: String
        when {
            httpException.code() == 500 -> {
                msg = "服务器发生错误"
            }
            httpException.code() == 404 -> {
                msg = "请求地址不存在"
            }
            httpException.code() == 403 -> {
                msg = "请求被服务器拒绝"
            }
            httpException.code() == 307 -> {
                msg = "请求被重定向到其他页面"
            }
            else -> {
                msg = httpException.message()
            }
        }
        return msg
    }
}