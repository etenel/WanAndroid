package com.wls.poke.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.wls.poke.base.result
import com.wls.poke.entity.BannerEntity
import com.wls.poke.entity.HomeArticleEntity
import com.wls.poke.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

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
        viewModelScope.launch {
            repository.banners().result {
                _banner.value = it
            }.collect {
            }


        }
    }

    fun collectArticle(article: HomeArticleEntity.Data) {
        viewModelScope.launch {
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




