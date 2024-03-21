package com.wls.poke.ui.home.di

import com.wls.base.IRepository
import com.wls.poke.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface HomeModule {
    @Binds
    fun bindsHomeRepository(homeRepository: HomeRepository): IRepository
}