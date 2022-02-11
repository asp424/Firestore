package com.lm.repository.data.repository.registration

import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.TimeUnit

interface RegRepository {

    suspend fun startAuth(phone: String, timeOut: Long): Flow<RegResponse>

    suspend fun authWithCode(id: String, code: String): Flow<RegResponse>

    fun signOut()

    class Base (
        private val phoneAuthOptionsBuilder: PhoneAuthOptions.Builder,
        private val statusCollector: StatusCollector,
        private val authRepository: AuthRepository
    ) : RegRepository {

        @OptIn(ExperimentalCoroutinesApi::class)
        override suspend fun startAuth(phone: String, timeOut: Long) = callbackFlow {
            runCatching {
                PhoneAuthProvider.verifyPhoneNumber(
                    phoneAuthOptionsBuilder
                        .setPhoneNumber(phone)
                        .setTimeout(timeOut, TimeUnit.SECONDS)
                        .setCallbacks(
                            RegCallback { response ->
                            statusCollector.collect(response) { trySendBlocking(it) }
                        }).build()
                )
            }
            awaitClose()
        }

        override suspend fun authWithCode(id: String, code: String): Flow<RegResponse> =
            authRepository.authWithCode(id, code)

        override fun signOut() = authRepository.signOut()

    }
}