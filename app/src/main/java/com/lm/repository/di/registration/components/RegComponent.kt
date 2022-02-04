package com.lm.repository.di.registration.components

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.lm.repository.RegistrationActivity
import com.lm.repository.di.registration.modules.AuthRepositoryModule
import com.lm.repository.di.registration.modules.RegRepositoryModule
import com.lm.repository.di.registration.modules.RegViewModelFactoryModule
import com.lm.repository.di.registration.modules.StatusCollectorModule
import com.lm.repository.ui.viewmodelsfactory.ViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [RegRepositoryModule::class,
        RegViewModelFactoryModule::class,
        StatusCollectorModule::class, AuthRepositoryModule::class]
)
@Singleton
interface RegComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun authOptions(authOptions: PhoneAuthOptions.Builder): Builder

        @BindsInstance
        fun authInstance(authInstance: FirebaseAuth): Builder

        fun create(): RegComponent
    }

    fun inject(regActivity: RegistrationActivity)

    fun authInstance(): FirebaseAuth

    fun viewModelFactory(): ViewModelFactory

}