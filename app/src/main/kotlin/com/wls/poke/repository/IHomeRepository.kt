package com.wls.poke.repository

import com.wls.poke.entity.BannerEntity
import com.wls.poke.entity.BaseData
import kotlinx.coroutines.flow.Flow

interface IHomeRepository {
 fun banners():  Flow<BaseData<List<BannerEntity>>>
}