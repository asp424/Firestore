package com.lm.repository.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lm.repository.data.repository.regrepository.RegRepository
import com.lm.repository.data.repository.regrepository.RegResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegViewModel @Inject constructor(
    private val regRepository: RegRepository
) : ViewModel() {

    private val _status: MutableStateFlow<RegResponse> = MutableStateFlow(RegResponse.StartAuth)

    val status: StateFlow<RegResponse> get() = _status


    fun startAuth() {
        viewModelScope.launch(Dispatchers.IO) {
            regRepository.startAuth("+10000000000", 60L).collect {

                _status.value = it
                when (it) {
                    is RegResponse.OnSuccess -> Log.d("My", it.uid.toString())
                    is RegResponse.SmsCode -> Log.d("My", it.smsCode.toString())
                    is RegResponse.OnError -> Log.d("My", it.exception.toString())
                    is RegResponse.RegId -> it.id?.let { it1 -> authWithCode(it1, "123456") }
                    else -> {}
                }
            }
        }
    }

    fun authWithCode(id: String, code: String) = viewModelScope.launch(Dispatchers.IO) {
        regRepository.authWithCode(id, code).collect {
            when (it) {
                is RegResponse.OnSuccess -> Log.d("My", it.uid.toString())
                is RegResponse.OnError -> Log.d("My", it.exception.toString())
                else -> {}
            }
        }
    }
}