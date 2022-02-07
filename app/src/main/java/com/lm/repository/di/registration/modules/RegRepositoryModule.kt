package com.lm.repository.di.registration.modules

import com.lm.repository.data.repository.registration.RegRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RegRepositoryModule {

    @Binds
    @Singleton
    fun bindsRegRepository(regRepository: RegRepository.Base): RegRepository
}