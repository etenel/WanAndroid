package com.wls.poke.ui.articledetail.repository

import com.wls.base.IRepository
import com.wls.poke.http.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ArticleDetailRepository @Inject constructor(private val service: ApiService) : IRepository {

    suspend fun collectArticle(id: Int,collect: Boolean) =
        flowOf(if (collect)service.cancelCollect(id)else service.collect(id)).flowOn(Dispatchers.IO)


}