package com.wls.poke.ui.articledetail.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import com.wls.base.BaseViewModel
import com.wls.base.entity.result
import com.wls.poke.base.MyApp
import com.wls.poke.ui.articledetail.repository.ArticleDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(private val repository: ArticleDetailRepository) :
    BaseViewModel<Unit>() {
    val collectMap = mutableStateMapOf<String, Boolean>()
    fun collectArticle(id: Int, link: String, collect: Boolean) {
        launch {
            repository.collectArticle(id, collect).result {
                if (collect) {
                    if (link in collectMap) {
                        collectMap.remove(link)
                    }
                    if (id in MyApp.appViewModel.articleCollectList) {
                        MyApp.appViewModel.articleCollectList.remove(id)
                    }
                } else {
                    collectMap[link] = true
                    MyApp.appViewModel.articleCollectList[id] = true
                }
            }.collect()
        }
    }


}