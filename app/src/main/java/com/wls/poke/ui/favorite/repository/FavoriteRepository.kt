package com.wls.poke.ui.favorite.repository

import com.wls.base.IRepository
import com.wls.poke.http.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val service: ApiService) : IRepository {
    suspend fun collectArticles(page: Int) = flowOf(
        service.collectArticles(page)
    ).flowOn(Dispatchers.IO)

    suspend fun cancelCollect(id: Int, originId: Int) = flowOf(
        service.cancelCollectArticle(id,originId)
    ).flowOn(Dispatchers.IO)
}