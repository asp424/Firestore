package com.lm.repository.di.registration.mapmodule

import com.lm.repository.di.app.modules.SharedPrefProviderModule
import com.lm.repository.di.registration.modules.AuthRepositoryModule
import com.lm.repository.di.registration.modules.RegRepositoryModule
import com.lm.repository.di.registration.modules.RegViewModelFactoryModule
import com.lm.repository.di.registration.modules.StatusCollectorModule
import dagger.Module

@Module(
    includes = [RegRepositoryModule::class,
        RegViewModelFactoryModule::class,
        StatusCollectorModule::class,
        AuthRepositoryModule::class,
        SharedPrefProviderModule::class]
)
class RegMapModule