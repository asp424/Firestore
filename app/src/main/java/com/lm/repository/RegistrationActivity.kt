package com.lm.repository

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.lm.repository.core.regComponentBuilder
import com.lm.repository.ui.viewmodels.RegViewModel

class RegistrationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseAuth.getInstance().apply {
            if (currentUser != null) {
                startActivity(Intent(this@RegistrationActivity,
                    MainActivity::class.java))
                    finish()
            }
            else {
                regComponentBuilder.authOptions(PhoneAuthOptions.newBuilder(this)
                            .setActivity(this@RegistrationActivity)
                    ).authInstance(this).create().viewModelFactory().apply {
                        val regViewModel: RegViewModel by viewModels { this }
                    regViewModel.startAuth()
                    }
            }
        }
    }
}