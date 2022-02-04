package com.lm.repository.di.app.components

import android.app.Application
import com.lm.repository.MainActivity
import com.lm.repository.di.app.mapmodule.MapModule
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