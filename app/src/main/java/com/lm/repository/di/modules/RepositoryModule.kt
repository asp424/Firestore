package com.lm.repository.di.modules

import com.lm.repository.data.repository.Repository
import com.lm.repository.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsRepository(repository: RepositoryImpl): Repository
}