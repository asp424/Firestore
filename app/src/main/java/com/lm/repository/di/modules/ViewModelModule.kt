package com.lm.repository.di.modules

import androidx.lifecycle.ViewModel
import com.lm.repository.ui.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap





@Module
interface ViewModelModule {
    @IntoMap
    @Binds
    @ViewModelKey(MainViewModel::class)
    fun bindsRegisterViewModel(viewModel: MainViewModel): ViewModel
}