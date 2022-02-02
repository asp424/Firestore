package com.lm.di.modules

import androidx.lifecycle.ViewModelProvider
import com.lm.ui.viewmodels.viewmodelsfactory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module(includes = [ViewModelModule::class])
interface ViewModelFactoryModule {

    @Binds
    fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}