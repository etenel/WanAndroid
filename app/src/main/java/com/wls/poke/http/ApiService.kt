package com.wls.poke.http

import com.wls.poke.entity.BannerEntity
import com.wls.base.entity.BaseData
import com.wls.poke.entity.HomeArticleEntity
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("api/lock/houseinfo")
    suspend fun anner(@Field("user_id") userId: String, @Field("role_type") int: Int): Any

    //轮播图
    @GET("banner/json")
    suspend fun getBanner(): BaseData<List<BannerEntity>>

    //首页文章列表
    @GET("article/list/{page}/json")
    suspend fun homeArticles(
        @Path("page") page: Int,
        @Query("page_size") pageSize: Int = 20
    ): BaseData<HomeArticleEntity>

    //文章列表取消收藏
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun cancelCollect(@Path("id") chapterId: Int): BaseData<Any>

    //收藏
    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int): BaseData<Any>

}