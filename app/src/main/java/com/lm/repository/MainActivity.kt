package com.lm.repository

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.lm.repository.core.appComponentBuilder
import com.lm.repository.ui.screens.MainScreen
import com.lm.repository.ui.viewmodels.MainViewModel
import com.lm.repository.ui.viewmodels.RegViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MainActivity : ComponentActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("My", auth.currentUser?.uid.toString())

        appComponentBuilder.authOptions(
            PhoneAuthOptions.newBuilder(auth).setActivity(this@MainActivity))
            .authInstance(auth)
            .create()
            .apply {
            val mVm: MainViewModel by viewModels { viewModelFactory() }
            val rVm: RegViewModel by viewModels { viewModelFactory() }
            setContent { MainScreen(mVm, rVm, sharedPreferences(), auth) }
        }
    }
}


