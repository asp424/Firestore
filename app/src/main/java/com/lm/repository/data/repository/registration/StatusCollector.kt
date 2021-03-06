package com.lm.repository.data.repository.registration

import com.google.firebase.auth.FirebaseAuth
import com.lm.repository.core.SharedPrefProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

interface StatusCollector {

    fun collect(response: RegResponse, onDone: (RegResponse) -> Unit)

    class Base(
        private val auth: FirebaseAuth,
        private val authRepository: AuthRepository,
        private val dispatcher: CoroutineDispatcher,
        private val sharedPrefProvider: SharedPrefProvider,
    ) : StatusCollector {
        override fun collect(response: RegResponse, onDone: (RegResponse) -> Unit) {
            when (response) {
                is RegResponse.Credential -> {
                    onDone(RegResponse.SmsCode(response.credential?.smsCode.toString()))
                    auth.currentUser?.uid.also { id ->
                        if (id == null) CoroutineScope(dispatcher).launch {
                            response.credential?.let { cred ->
                                authRepository.authWithCredential(cred).collect {
                                    onDone(it)
                                    cancel()
                                    }
                            }
                        }
                        else onDone(RegResponse.OnSuccess(id))
                    }
                }

                is RegResponse.OnError -> onDone(response)
                is RegResponse.RegId -> {
                    sharedPrefProvider.save(response.id.toString())
                    onDone(response)
                }
                else -> Unit
            }
        }
    }
}
