package com.lm.repository.di.app.modules

import com.lm.repository.data.sources.firestoresource.FirestoreSource
import com.lm.repository.data.sources.firestoresource.FirestoreSourceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface FirestoreSourceModule {

    @Binds
    @Singleton
    fun bindsFirestoreSource(fireStoreSource: FirestoreSourceImpl): FirestoreSource
}