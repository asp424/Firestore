package com.lm.di.modules

import com.lm.data.repository.Repository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsRepository(repository: Repository.Base): Repository
}