package com.lm.repository.ui.viewmodelsfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lm.repository.data.repository.firestore.FirestoreRepository
import com.lm.repository.data.repository.internet.InternetStatusProvider
import com.lm.repository.data.repository.registration.RegRepository
import com.lm.repository.ui.viewmodels.MainViewModel
import com.lm.repository.ui.viewmodels.RegViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MainViewModelFactory (
    private val firestoreRepository: FirestoreRepository,
    private val dispatcher: CoroutineDispatcher,
    private val internetStatusProvider: InternetStatusProvider
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainViewModel(
            firestoreRepository, dispatcher, internetStatusProvider
        ) as T
}

class RegViewModelFactory (
    private val regRepository: RegRepository
) : ViewModelProvider.Factory {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        RegViewModel(
            regRepository
        ) as T
}

