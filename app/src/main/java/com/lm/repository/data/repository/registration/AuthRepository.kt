package com.lm.repository.data.repository.registration

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface AuthRepository {

    suspend fun authWithCode(id: String, code: String): Flow<RegResponse>

    suspend fun authWithCredential(credential: PhoneAuthCredential): Flow<RegResponse>

    fun signOut()

    class Base (private val auth: FirebaseAuth) : AuthRepository {

        override suspend fun authWithCode(id: String, code: String): Flow<RegResponse> =
            authWithCredential(PhoneAuthProvider.getCredential(id, code))

        @OptIn(ExperimentalCoroutinesApi::class)
        override suspend fun authWithCredential(credential: PhoneAuthCredential) = callbackFlow {

            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful)
                        trySendBlocking(RegResponse.OnSuccess(task.result?.user?.uid)) }

                .addOnFailureListener { trySendBlocking(RegResponse.OnError(it.message)) }

            awaitClose()
        }

        override fun signOut() = auth.signOut()

    }
}