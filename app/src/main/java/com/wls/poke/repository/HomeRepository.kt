package com.wls.poke.repository

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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
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
                prefetchDistance = 1,
                initialLoadSize = 1
            )
        ) {
            ArticlesPagingSource(service)
        }.flow
    }

    suspend fun cancelCollect(chapterId: Int): Flow<BaseData<Any>> {
        return withContext(Dispatchers.IO) {
            flowOf(service.cancelCollect(chapterId))

        }

    }

    suspend fun collect(id: Int): Flow<BaseData<Any>> {
        return withContext(Dispatchers.IO) {
            flow {
                emit(service.collect(id))
            }
        }

    }


}