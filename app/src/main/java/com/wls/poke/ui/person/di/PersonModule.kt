package com.wls.poke.ui.person.di

import com.wls.base.IRepository
import com.wls.poke.ui.person.repository.PersonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface PersonModule {
    @Binds
    fun bindPersonRepository(repository: PersonRepository): IRepository

}