@file:OptIn(ExperimentalPagingApi::class)

package com.wls.poke.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wls.base.IRepository
import com.wls.base.entity.BaseData
import com.wls.poke.entity.BannerEntity
import com.wls.poke.entity.HomeArticleEntity
import com.wls.poke.http.ApiService
import com.wls.poke.paging.ArticlesPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository @Inject constructor(private val service: ApiService) : IRepository {
    suspend fun banners(): Flow<BaseData<List<BannerEntity>>> =
        withContext(Dispatchers.IO) {
            flowOf(service.getBanner())
        }

    fun articles(): Flow<PagingData<HomeArticleEntity.Data>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 4,
                initialLoadSize = 1,
                enablePlaceholders = true
            ),
          //  remoteMediator = ArticlesRemoteMediator(service)
        ) {
            ArticlesPagingSource(service)
        }.flow
    }


    suspend fun collectArticle(id: Int, collect: Boolean) =
        flowOf(if (!collect) service.collect(id) else service.cancelCollect(id)).flowOn(Dispatchers.IO)


}