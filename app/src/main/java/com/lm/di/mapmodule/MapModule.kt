package com.lm.di.mapmodule

import com.lm.di.modules.FirestoreSourceModule
import com.lm.di.modules.RepositoryModule
import com.lm.di.modules.ViewModelFactoryModule
import dagger.Module

@Module(
    includes = [FirestoreSourceModule::class,
        ViewModelFactoryModule::class,
        RepositoryModule::class]
)
class MapModule