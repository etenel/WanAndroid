package com.wls.poke.http.cookie

import com.wls.base.utils.LogUtils
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import javax.inject.Inject

class LoginCookieJar @Inject constructor(private val cookieStore: CookieStore) : CookieJar {
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        cookieStore.getCookies()
       return cookieStore.get(url)
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        LogUtils.e(url)
        LogUtils.e(cookies)
        cookieStore.add(url, cookies)
    }
}