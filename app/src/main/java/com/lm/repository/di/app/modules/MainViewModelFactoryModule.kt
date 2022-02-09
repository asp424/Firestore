package com.lm.repository.di.app.modules

import androidx.lifecycle.ViewModelProvider
import com.lm.repository.di.app.modules.MainViewModelModule
import com.lm.repository.di.registration.modules.RegViewModelModule
import com.lm.repository.ui.viewmodelsfactory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module(
    includes = [MainViewModelModule::class, RegViewModelModule::class]
)
interface MainViewModelFactoryModule {

    @Binds
    fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}