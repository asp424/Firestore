package com.lm.repository.di.app.mapmodule

import com.lm.repository.di.app.modules.FirestoreRepositoryModule
import com.lm.repository.di.app.modules.FirestoreSourceModule
import com.lm.repository.di.app.modules.MainViewModelFactoryModule
import com.lm.repository.di.app.modules.SharedPrefProviderModule
import dagger.Module

@Module(
    includes = [FirestoreSourceModule::class,
        MainViewModelFactoryModule::class,
        FirestoreRepositoryModule::class,
    SharedPrefProviderModule::class]
)
class AppMapModule