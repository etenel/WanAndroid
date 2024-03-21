package com.wls.poke.ui.articledetail.viewmodel

import com.wls.base.BaseViewModel
import com.wls.poke.ui.articledetail.repository.ArticleDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(private val repository: ArticleDetailRepository) :
    BaseViewModel<Unit>() {

}