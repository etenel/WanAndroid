package com.wls.poke.http

import com.wls.base.BaseApp.Companion.appContext
import com.wls.base.utils.NetworkUtil
import com.wls.poke.http.cookie.LoginCookieJar
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import java.io.File
import javax.inject.Inject


class RetrofitClient @Inject constructor(
    private val loginCookieJar: LoginCookieJar,
) : BaseRetrofitClient() {
    val service by lazy {
        getService(ApiService::class.java, Api.BASE_URL)
    }
    override fun handleBuilder(builder: OkHttpClient.Builder) {

        val httpCacheDirectory = File(appContext.cacheDir, "responses")
        val cacheSize = 10 * 1024 * 1024L // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize)
        builder.cache(cache)
            .cookieJar(loginCookieJar)
            .addInterceptor { chain ->
                var request = chain.request()

                if (!NetworkUtil.isNetworkAvailable(appContext)) {
                    request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
                }
                val response = chain.proceed(request)
                if (!NetworkUtil.isNetworkAvailable(appContext) ) {
                    val maxAge = 60 * 60
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .build()
                } else {
                        val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                        response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                            .build()

                }
                response
            }
    }

}