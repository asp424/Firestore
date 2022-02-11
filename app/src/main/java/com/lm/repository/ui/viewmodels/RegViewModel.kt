package com.lm.repository.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lm.repository.data.repository.registration.RegRepository
import com.lm.repository.data.repository.registration.RegResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class RegViewModel(
    private val regRepository: RegRepository
) : ViewModel() {

    suspend fun startAuth(phone: String, timeOut: Long): StateFlow<RegResponse> =
        with(regRepository) {
            this.startAuth(phone, timeOut).flatMapLatest { flowOf(it) }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = RegResponse.StartAuth
            )
        }

    suspend fun authWithCode(id: String, code: String): StateFlow<RegResponse> =
        with(regRepository) {
            this.authWithCode(id, code).flatMapLatest { flowOf(it) }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = RegResponse.StartAuth
            )
        }

    fun signOut() = regRepository.signOut()
}