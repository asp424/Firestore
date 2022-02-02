package com.lm.di.modules

import com.lm.repository.FirestoreSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface FirestoreSourceModule {

    @Binds
    @Singleton
    fun bindsFirestoreSource(fireStoreSource: FirestoreSource.Base): FirestoreSource
}