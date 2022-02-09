package com.lm.repository.di.app.mapmodule

import com.lm.repository.di.app.modules.FirestoreRepositoryModule
import com.lm.repository.di.app.modules.FirestoreSourceModule
import com.lm.repository.di.app.modules.MainViewModelFactoryModule
import com.lm.repository.di.app.modules.SharedPrefProviderModule
import com.lm.repository.di.registration.modules.AuthRepositoryModule
import com.lm.repository.di.registration.modules.RegRepositoryModule
import com.lm.repository.di.registration.modules.RegViewModelFactoryModule
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
        AuthRepositoryModule::class,
    ]
)
class AppMapModule