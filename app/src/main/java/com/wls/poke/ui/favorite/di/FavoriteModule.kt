package com.wls.poke.ui.favorite.di

import com.wls.base.IRepository
import com.wls.poke.ui.favorite.repository.FavoriteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FavoriteModule {
    @Binds
    fun bindFavoriteRepository(repository: FavoriteRepository): IRepository

}