package com.wls.poke.http

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    val isOnline:Flow<Boolean>
}