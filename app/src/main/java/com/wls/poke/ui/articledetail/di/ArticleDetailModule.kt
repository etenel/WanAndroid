package com.wls.poke.ui.articledetail.di

import com.wls.base.IRepository
import com.wls.poke.ui.articledetail.repository.ArticleDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ArticleDetailModule {
    @Binds
    fun bindArticleDetailRepository(repository: ArticleDetailRepository): IRepository

}