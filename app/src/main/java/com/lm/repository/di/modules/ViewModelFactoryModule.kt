package com.lm.repository.di.modules

import androidx.lifecycle.ViewModelProvider
import com.lm.repository.ui.viewmodelsfactory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module(includes = [ViewModelModule::class])
interface ViewModelFactoryModule {

    @Binds
    fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}