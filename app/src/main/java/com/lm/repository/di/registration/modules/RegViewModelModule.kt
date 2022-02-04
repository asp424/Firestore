package com.lm.repository.di.registration.modules

import androidx.lifecycle.ViewModel
import com.lm.repository.di.main.viewmodelkey.ViewModelKey
import com.lm.repository.ui.viewmodels.RegViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface RegViewModelModule {
    @IntoMap
    @Binds
    @ViewModelKey(RegViewModel::class)
    fun bindsRegViewModel(viewModel: RegViewModel): ViewModel
}