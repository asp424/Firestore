package com.lm.repository

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.lm.repository.data.repository.regrepository.RegResponse
import com.lm.repository.di.registration.components.DaggerRegComponent
import com.lm.repository.ui.viewmodels.RegViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class RegistrationActivity : ComponentActivity() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseAuth.getInstance().apply {
            if (currentUser != null) {
                startActivity(
                    Intent(this@RegistrationActivity, MainActivity::class.java)
                )
                finish()
            } else {
                DaggerRegComponent.builder().dispatcher(Dispatchers.IO).authOptions(
                    PhoneAuthOptions.newBuilder(this).setActivity(this@RegistrationActivity)
                ).authInstance(this).create().viewModelFactory().apply {
                    val regViewModel by viewModels<RegViewModel> { this }
                    with( regViewModel )  {
                        lifecycleScope.launch {
                           startAuth("+79822739593", 60L).collect(::authCollector)
                        }
                    }
                }
            }
        }
    }

    private fun authCollector(response: RegResponse) = with(response) {
        when (this) {
            is RegResponse.OnSuccess -> Log.d("My", uid.toString())
            is RegResponse.SmsCode -> Log.d("My", smsCode.toString())
            is RegResponse.OnError -> Log.d("My", exception.toString())
            is RegResponse.RegId -> Log.d("My", id.toString())
            else -> {}
        }
    }
}