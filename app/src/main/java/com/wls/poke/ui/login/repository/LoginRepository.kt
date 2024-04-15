package com.wls.poke.ui.login.repository

import com.wls.base.IRepository
import com.wls.poke.http.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class LoginRepository @Inject constructor(private val service: ApiService) : IRepository {

    suspend fun login(account: String, password: String) =
        flowOf(service.login(account, password)).onStart { delay(1000) }
            .flowOn(Dispatchers.IO)
}