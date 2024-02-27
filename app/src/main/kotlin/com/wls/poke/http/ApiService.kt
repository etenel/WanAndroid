package com.wls.poke.http

import com.wls.poke.entity.BannerEntity
import com.wls.poke.entity.BaseData
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("api/lock/houseinfo")
    suspend fun anner(@Field("user_id") userId: String, @Field("role_type") int: Int): Any


    @GET("banner/json")
    suspend fun getBanner(): BaseData<List<BannerEntity>>

}