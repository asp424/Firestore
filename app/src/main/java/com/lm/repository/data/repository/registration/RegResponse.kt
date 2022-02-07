package com.lm.repository.data.repository.registration

import com.google.firebase.auth.PhoneAuthCredential


sealed class RegResponse {
    data class OnSuccess(val uid: String?) : RegResponse()
    data class OnError(val exception: String?) : RegResponse()
    data class Credential(val credential: PhoneAuthCredential) : RegResponse()
    data class SmsCode(val smsCode: String?) : RegResponse()
    data class RegId(val id: String?) : RegResponse()
    object StartAuth : RegResponse()

}