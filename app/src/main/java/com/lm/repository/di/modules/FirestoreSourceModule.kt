package com.lm.repository.di.modules

import com.lm.repository.data.sources.FirestoreSource
import com.lm.repository.data.sources.FirestoreSourceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface FirestoreSourceModule {

    @Binds
    @Singleton
    fun bindsFirestoreSource(fireStoreSource: FirestoreSourceImpl): FirestoreSource
}