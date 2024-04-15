package com.wls.poke.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.wls.base.BaseViewModel
import com.wls.base.entity.result
import com.wls.poke.base.MyApp
import com.wls.poke.entity.BannerEntity
import com.wls.poke.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) :
    BaseViewModel<Any>() {
    private val _banner = MutableStateFlow<List<BannerEntity>>(emptyList())
    val banner get() = _banner
    val homeArticles by lazy { repository.articles().cachedIn(viewModelScope) }



    init {
        refresh()
    }

    fun refresh() {
        banner()
    }

    private fun banner() {
        launch {
            repository.banners().result {
                if (it != null) _banner.value = it
            }.collect()
        }
    }

    fun collectArticle(id: Int, collect: Boolean) {
        launch {
            repository.collectArticle(id,collect).result{
               if (id in MyApp.appViewModel.articleCollectList){
                   MyApp.appViewModel.articleCollectList.remove(id)
               }else{
                   MyApp.appViewModel.articleCollectList[id]=true
               }
            }.collect()
        }
    }


}




