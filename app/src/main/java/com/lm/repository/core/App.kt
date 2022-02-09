package com.lm.repository.core

import android.app.Application
import android.content.Context
import com.lm.repository.di.app.components.DaggerAppComponent
import kotlinx.coroutines.Dispatchers

class App : Application() {
    val appComponentBuilder by lazy {
        DaggerAppComponent.builder()
            .sharedPreferences(getSharedPreferences("id", MODE_PRIVATE))
            .dispatcher(Dispatchers.IO)
    }
}

val Context.appComponentBuilder get() = (applicationContext as App).appComponentBuilder


