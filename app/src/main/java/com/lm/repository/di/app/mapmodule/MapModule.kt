package com.lm.repository.di.app.mapmodule

import com.lm.repository.di.app.modules.FirestoreRepositoryModule
import com.lm.repository.di.app.modules.FirestoreSourceModule
import com.lm.repository.di.main.modules.MainViewModelFactoryModule
import dagger.Module

@Module(
    includes = [FirestoreSourceModule::class,
        MainViewModelFactoryModule::class,
        FirestoreRepositoryModule::class]
)
class MapModule