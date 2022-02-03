package com.lm.repository.core

import android.app.Application
import com.lm.repository.MainActivity
import com.lm.repository.di.DaggerAppComponent

class App : Application() {

    val appComponent by lazy { DaggerAppComponent.builder().application(this).create() }
}

val MainActivity.appComponent get() = (applicationContext as App).appComponent