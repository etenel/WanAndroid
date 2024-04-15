@file:OptIn(ExperimentalPagingApi::class)

package com.wls.poke.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.wls.base.utils.LogUtils
import com.wls.poke.entity.HomeArticleEntity
import com.wls.poke.http.ApiService

class ArticlesRemoteMediator(service: ApiService) : RemoteMediator<Int, HomeArticleEntity.Data>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HomeArticleEntity.Data>,
    ): MediatorResult {
        LogUtils.e(loadType.name)
        return when (loadType) {

            LoadType.REFRESH -> {
                MediatorResult.Success(false)
            }

            LoadType.PREPEND -> {
                MediatorResult.Success(true)
            }

            LoadType.APPEND -> {
                MediatorResult.Success(false)
            }
        }
    }

}