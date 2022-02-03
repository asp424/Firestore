package com.lm.repository.di.mapmodule

import com.lm.repository.di.modules.FirestoreSourceModule
import com.lm.repository.di.modules.RepositoryModule
import com.lm.repository.di.modules.ViewModelFactoryModule
import dagger.Module

@Module(
    includes = [FirestoreSourceModule::class,
        ViewModelFactoryModule::class,
        RepositoryModule::class]
)
class MapModule