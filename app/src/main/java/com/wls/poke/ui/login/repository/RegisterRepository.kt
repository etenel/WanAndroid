package com.wls.poke.ui.login.repository

import com.wls.base.IRepository
import com.wls.poke.http.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val service: ApiService) : IRepository {
    suspend fun registry(account: String, password: String, rePassword: String) = withContext(Dispatchers.IO){
        flowOf(service.register(account,password, rePassword))
    }


}