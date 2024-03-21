package com.wls.poke.ui.login.repository

import com.wls.base.IRepository
import com.wls.base.entity.BaseData
import com.wls.base.entity.Null
import com.wls.poke.http.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepository @Inject constructor(private val service: ApiService):IRepository{

    suspend fun login(account: String, password: String): Flow<BaseData<Null>> =
        withContext(Dispatchers.IO) {
            flowOf(service.login(account, password))
        }
}