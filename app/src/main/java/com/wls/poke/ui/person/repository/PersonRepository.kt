package com.wls.poke.ui.person.repository

import com.wls.base.IRepository
import com.wls.poke.http.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PersonRepository @Inject constructor(private val service: ApiService) : IRepository {

    suspend fun userInfo() =
        flowOf(service.userInfo()).flowOn(Dispatchers.IO)



}