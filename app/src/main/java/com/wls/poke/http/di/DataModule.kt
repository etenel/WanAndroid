package com.wls.poke.http.di

import com.wls.poke.http.ConnectivityManagerNetworkMonitor
import com.wls.poke.http.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
@Binds
fun bindsNetworkMonitor(networkMonitor: ConnectivityManagerNetworkMonitor):NetworkMonitor
}