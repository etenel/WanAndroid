package com.wls.poke.http

import com.wls.poke.base.MyApplication
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitClient : BaseRetrofitClient() {
    @Singleton
    val service by lazy {
        getService(ApiService::class.java, Api.BASE_URL)
    }
    private val netWorkUtils = ConnectivityManagerNetworkMonitor.networkMonitor
    override fun handleBuilder(builder: OkHttpClient.Builder) {

        val httpCacheDirectory = File(MyApplication.CONTEXT.cacheDir, "responses")
        val cacheSize = 100 * 1024 * 1024L // 100 MiB
        val cache = Cache(httpCacheDirectory, cacheSize)
        builder.cache(cache)
            //  .cookieJar(cookieJar)
            .addInterceptor { chain ->
                var request = chain.request()

                if (!netWorkUtils.isNetworkAvailable()) {
                    request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
                }
                val response = chain.proceed(request)
                if (!netWorkUtils.isNetworkAvailable()) {
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