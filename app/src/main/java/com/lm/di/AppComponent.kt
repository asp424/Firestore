package com.lm.di

import android.app.Application
import com.lm.di.mapmodule.MapModule
import com.lm.repository.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [MapModule::class])
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder
        fun create(): AppComponent
    }

    fun inject(mainActivity: MainActivity)
}