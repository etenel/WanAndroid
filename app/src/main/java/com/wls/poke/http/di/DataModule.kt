package com.wls.poke.http.di

import com.wls.poke.http.ApiService
import com.wls.poke.http.ConnectivityManagerNetworkMonitor
import com.wls.poke.http.NetworkMonitor
import com.wls.poke.http.RetrofitClient
import com.wls.poke.http.cookie.CookieStore
import com.wls.poke.http.cookie.LoginCookieJar
import com.wls.poke.http.cookie.PersistentCookieStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsNetworkMonitor(networkMonitor: ConnectivityManagerNetworkMonitor): NetworkMonitor

    @Binds
    fun bindsCookieStore(cookieStore: PersistentCookieStore): CookieStore


}
@Module
@InstallIn(SingletonComponent::class)
object HttpModule {
    @Singleton
    @Provides
    fun provideRetrofitClientService(loginCookieJar: LoginCookieJar): ApiService = RetrofitClient(loginCookieJar).service


}