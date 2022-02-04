package com.lm.repository.data.repository.regrepository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

interface StatusCollector {

    fun collect(response: RegResponse, onDone: (RegResponse) -> Unit)

    class Base @Inject constructor(
        private val auth: FirebaseAuth,
        private val authRepository: AuthRepository
    ): StatusCollector{
        override fun collect(response: RegResponse, onDone: (RegResponse) -> Unit) {
            when (response) {
                is RegResponse.Credential -> {
                    if (auth.currentUser?.uid == null) CoroutineScope(Dispatchers.IO).launch {
                        authRepository.authWithCredential(response.credential).collect { onDone(it) }
                        onDone(RegResponse.SmsCode(response.credential.smsCode.toString())) }
                    else onDone(RegResponse.OnSuccess(auth.currentUser?.uid))
                }
                is RegResponse.OnError -> onDone(response)
                is RegResponse.RegId -> onDone(response)
                else -> Unit
            }
        }
    }
}