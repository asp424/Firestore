package com.lm.repository.di.registration.modules

import com.lm.repository.data.repository.registration.StatusCollector
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface StatusCollectorModule {

    @Binds
    @Singleton
    fun bindsStatusCollector(statusCollector: StatusCollector.Base): StatusCollector
}