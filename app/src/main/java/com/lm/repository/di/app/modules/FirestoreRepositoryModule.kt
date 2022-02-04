package com.lm.repository.di.app.modules

import com.lm.repository.data.repository.firestore.FirestoreRepository
import com.lm.repository.data.repository.firestore.FirestoreRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface FirestoreRepositoryModule {

    @Binds
    @Singleton
    fun bindsRepository(repository: FirestoreRepositoryImpl): FirestoreRepository
}