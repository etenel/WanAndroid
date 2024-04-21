package com.wls.poke.ui.favorite.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wls.base.BaseViewModel
import com.wls.base.entity.result
import com.wls.poke.entity.CollectArticleEntity
import com.wls.poke.entity.HomeArticleEntity
import com.wls.poke.ui.component.ListData
import com.wls.poke.ui.favorite.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: FavoriteRepository) :
    BaseViewModel<HomeArticleEntity>() {
    val listData by mutableStateOf(
        ListData<CollectArticleEntity.Data>(mutableStateListOf())
    )
    private var curPage by mutableIntStateOf(0)

    init {
        articles(true)
    }

    fun articles(refresh: Boolean) {


        launch(
            finallyBlock = {
                if (it == null) {
                    listData.end(refresh)
                } else {
                    listData.error(refresh)
                }
            },
        ) {
            listData.start(refresh)
            var page = curPage
            if (refresh) {
                page = 0
            } else {
                page++
            }
            repository.collectArticles(page).result {
                if (it != null) {
                    if (it.datas.isNotEmpty()) {
                        curPage = page
                    }
                    listData.calculateData(refresh, it.datas)
                }
            }.collect()
        }
    }

    fun cancelCollect(article: CollectArticleEntity.Data) {
        launch {
            repository.cancelCollect(article.id, article.originId).result {
                listData.data.remove(article)
            }.collect()
        }


    }
}