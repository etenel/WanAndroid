package com.wls.poke.http

import com.wls.base.entity.BaseData
import com.wls.poke.entity.BannerEntity
import com.wls.poke.entity.CollectArticleEntity
import com.wls.poke.entity.HomeArticleEntity
import com.wls.poke.entity.UserEntity
import com.wls.poke.entity.UserInfoEntity
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //轮播图
    @GET("banner/json")
    suspend fun getBanner(): BaseData<List<BannerEntity>>

    //首页文章列表
    @GET("article/list/{page}/json")
    suspend fun homeArticles(
        @Path("page") page: Int,
        @Query("page_size") pageSize: Int = 20,
    ): BaseData<HomeArticleEntity>

    //收藏文章列表
    @GET("lg/collect/list/{page}/json")
    suspend fun collectArticles(
        @Path("page") page: Int,
        @Query("page_size") pageSize: Int = 20,
    ): BaseData<CollectArticleEntity>

    //文章列表取消收藏
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun cancelCollect(@Path("id") chapterId: Int): BaseData<Unit>
    //收藏页面取消收藏
    @POST("lg/uncollect/{id}/json")
    suspend fun cancelCollectArticle(@Path("id")id: Int,@Query("originId") originId:Int=-1):BaseData<Unit>

    //收藏站内文章
    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int): BaseData<Unit>

    //收藏站外文章
    @POST("lg/collect/add/json")
    suspend fun collect(
        @Field("title") title: String,
        @Field("author") author: String,
        @Field("link") link: String,
    ): BaseData<Unit>

    //登录
    @FormUrlEncoded
    @POST("user/login")
   suspend  fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): BaseData<UserEntity>

    //    注册
    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") rePassword: String,
    ): BaseData<UserEntity>

    //个人信息
    @GET("user/lg/userinfo/json")
    suspend fun userInfo(): BaseData<UserInfoEntity>

}