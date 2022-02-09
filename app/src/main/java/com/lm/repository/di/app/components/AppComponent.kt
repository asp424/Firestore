package com.lm.repository.di.app.components

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.lm.repository.core.SharedPrefProvider
import com.lm.repository.di.app.mapmodule.AppMapModule
import com.lm.repository.ui.viewmodelsfactory.ViewModelFactory
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Component(modules = [AppMapModule::class])
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun sharedPreferences(sharedPreferences: SharedPreferences): Builder

        @BindsInstance
        fun authOptions(authOptions: PhoneAuthOptions.Builder): Builder

        @BindsInstance
        fun authInstance(authInstance: FirebaseAuth): Builder

        @BindsInstance
        fun dispatcher(dispatcher: CoroutineDispatcher): Builder

        fun create(): AppComponent
    }

    fun viewModelFactory(): ViewModelFactory

    fun sharedPreferences(): SharedPrefProvider
}