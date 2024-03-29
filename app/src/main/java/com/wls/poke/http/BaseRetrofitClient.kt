package com.wls.poke.http

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.wls.poke.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


abstract class BaseRetrofitClient {

    companion object {
        private const val TIME_OUT = 30
    }

    private val client: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor()
//            { message ->
//            自定义打印
//                LogUtils.d(message)
//            }
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.NONE
            }
            builder.addInterceptor(logging)
                .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                //  .addInterceptor(CommonInterceptor())
                .followSslRedirects(true)
            //  .sslSocketFactory(HttpsUtils().getSslSocketFactory()?.sSLSocketFactory!!,HttpsUtils().UnSafeTrustManager)

            handleBuilder(builder)

            return builder.build()
        }

    protected abstract fun handleBuilder(builder: OkHttpClient.Builder)
    private val contentType = "application/json".toMediaType()
    fun <S> getService(serviceClass: Class<S>, baseUrl: String): S {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(Json.asConverterFactory(contentType))
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            //    .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .baseUrl(baseUrl)
            .build().create(serviceClass)
    }

}