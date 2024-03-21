package com.wls.poke.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.wls.base.BaseViewModel
import com.wls.base.entity.result
import com.wls.poke.entity.BannerEntity
import com.wls.poke.entity.HomeArticleEntity
import com.wls.poke.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : BaseViewModel<Unit>() {

    private val _banner = MutableStateFlow<List<BannerEntity>>(emptyList())
    val banner get() = _banner
    val homeArticles = repository.articles().cachedIn(viewModelScope)

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
            }.collect {
            }


        }
    }
        fun collectArticle(article: HomeArticleEntity.Data) {
            launch {
                if (article.collect) {
                    repository.cancelCollect(article.id)
                        .result {

                        }

                } else {
                    repository.collect(article.id).result {

                    }


                }
            }


        }


}




