package com.lm.repository

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.google.firebase.auth.PhoneAuthOptions
import com.lm.repository.di.app.components.DaggerAppComponent
import com.lm.repository.theme.bottomSheetContent
import com.lm.repository.theme.firebaseAuth
import com.lm.repository.ui.navigator.Navigator
import com.lm.repository.ui.viewmodels.MainViewModel
import com.lm.repository.ui.viewmodels.RegViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.builder()
            .authInstance(firebaseAuth)
            .sharedPreferences(getSharedPreferences("id", MODE_PRIVATE))
            .dispatcher(Dispatchers.IO).authOptions(
                PhoneAuthOptions.newBuilder(firebaseAuth).setActivity(this@MainActivity)
            ).create()
            .apply {
                val mVm: MainViewModel by viewModels { viewModelFactory() }
                val rVm: RegViewModel by viewModels { viewModelFactory() }
                setContent { Navigator(mVm, rVm, sharedPreferences(), firebaseAuth) }
            }
    }

    override fun onResume() {
        super.onResume()
        bottomSheetContent = ""
    }
}


