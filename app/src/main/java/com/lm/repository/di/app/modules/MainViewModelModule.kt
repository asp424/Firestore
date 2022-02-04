package com.lm.repository.di.app.modules

import androidx.lifecycle.ViewModel
import com.lm.repository.di.main.viewmodelkey.ViewModelKey
import com.lm.repository.ui.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainViewModelModule {
    @IntoMap
    @Binds
    @ViewModelKey(MainViewModel::class)
    fun bindsMainViewModel(viewModel: MainViewModel): ViewModel
}