package com.lm.repository.di.registration.modules

import com.lm.repository.data.repository.regrepository.AuthRepository
import com.lm.repository.data.repository.regrepository.RegRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface AuthRepositoryModule {

    @Binds
    @Singleton
    fun bindsAuthRepository(authRepository: AuthRepository.Base): AuthRepository
}

