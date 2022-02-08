package com.lm.repository

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.lm.repository.di.app.components.DaggerAppComponent
import com.lm.repository.ui.screens.MainScreen
import com.lm.repository.ui.screens.list
import com.lm.repository.ui.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerAppComponent.builder()
            .sharedPreferences(getSharedPreferences("id", MODE_PRIVATE))
            .dispatcher(Dispatchers.IO)
            .create().apply {
                val vm: MainViewModel by viewModels { viewModelFactory() }
                setContent { MainScreen(vm, sharedPreferences()) }
            }
    }
}


