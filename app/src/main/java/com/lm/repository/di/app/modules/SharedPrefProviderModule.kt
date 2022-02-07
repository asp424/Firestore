package com.lm.repository.di.app.modules

import com.lm.repository.core.SharedPrefProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
interface SharedPrefProviderModule {
    @Binds
    @Singleton
    fun provideSharedPreference(sharedPrefProvider: SharedPrefProvider.Base): SharedPrefProvider
}