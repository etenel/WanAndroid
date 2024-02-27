package com.wls.poke.repository

import com.wls.poke.entity.BannerEntity
import com.wls.poke.entity.BaseData
import com.wls.poke.http.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor() : IHomeRepository {
    override fun banners(): Flow<BaseData<List<BannerEntity>>> = flow {
        emit(RetrofitClient().service.getBanner())
    }



}