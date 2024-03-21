package com.wls.poke.ui.login.di

import com.wls.base.IRepository
import com.wls.poke.ui.login.repository.RegisterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LoginModule {

    @Binds
    fun bindRegisterRepository(registerRepository: RegisterRepository): IRepository

}