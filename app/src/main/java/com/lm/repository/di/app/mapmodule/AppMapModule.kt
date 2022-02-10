package com.lm.repository.di.app.mapmodule

import com.lm.repository.di.app.modules.*
import com.lm.repository.di.registration.modules.AuthRepositoryModule
import com.lm.repository.di.registration.modules.RegRepositoryModule
import com.lm.repository.di.registration.modules.StatusCollectorModule
import dagger.Module

@Module(
    includes = [
        FirestoreSourceModule::class,
        MainViewModelFactoryModule::class,
        FirestoreRepositoryModule::class,
        SharedPrefProviderModule::class,
        RegRepositoryModule::class,
        StatusCollectorModule::class,
        AuthRepositoryModule::class
    ]
)
class AppMapModule