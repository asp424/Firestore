package com.lm.repository

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.lm.repository.di.registration.components.DaggerRegComponent
import com.lm.repository.ui.screens.RegScreen
import com.lm.repository.ui.viewmodels.RegViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

class RegistrationActivity : ComponentActivity() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseAuth.getInstance().apply {
            if (currentUser != null) {
                startActivity(Intent(this@RegistrationActivity, MainActivity::class.java))
                finish()
            } else
                DaggerRegComponent.builder()
                    .dispatcher(Dispatchers.IO)
                    .authOptions(
                        PhoneAuthOptions.newBuilder(this)
                            .setActivity(this@RegistrationActivity)
                    )
                    .authInstance(this)
                    .sharedPreferences(getSharedPreferences("id", MODE_PRIVATE))
                    .create().apply {
                        sharedPreferences().apply {
                            viewModelFactory().also {

                                val regViewModel by viewModels<RegViewModel> { it }

                                setContent { RegScreen(
                                    regViewModel,
                                    this,
                                    this@RegistrationActivity) }
                            }
                        }
                    }
        }
    }
}


