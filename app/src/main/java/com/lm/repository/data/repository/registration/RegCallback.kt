package com.lm.repository.data.repository.registration

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.lm.repository.core.SharedPrefProvider

class RegCallback(
    private val result: (RegResponse) -> Unit
) :
    PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
        result(RegResponse.Credential(credential))
        Log.d("My", "cred")
    }

    override fun onVerificationFailed(exception: FirebaseException) {
        result(RegResponse.OnError(exception.message))
    Log.d("My", "err")
    }

    override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
        result(RegResponse.RegId(id))
        Log.d("My", "code")
    }
}
