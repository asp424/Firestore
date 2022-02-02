package com.lm.core

import android.app.Activity
import android.app.Application
import com.lm.di.DaggerAppComponent
import com.lm.repository.MainActivity

class App: Application() {

    val appComponent by lazy { DaggerAppComponent.builder().application(this).create()  }
}

val MainActivity.appComponent get() = (applicationContext as App).appComponent