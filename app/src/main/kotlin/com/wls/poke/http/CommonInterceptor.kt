package com.wls.poke.http

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class CommonInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val httpUrl = oldRequest.url
        val host = httpUrl.host
//        if (HttpConstant.HTTP_SERVER != host ) {
//            return chain.proceed(oldRequest)
//        }
        val urlBuilder = httpUrl.newBuilder()
        //这里填写自己申请appid和sign
        urlBuilder.addQueryParameter("showapi_appid", "SHOW_API_APPID")
        urlBuilder.addQueryParameter("showapi_sign", "SHOW_API_SIGN")

        val request = oldRequest
            .newBuilder()
            .url(urlBuilder.build())
            .build()
        return chain.proceed(request)
    }
}
