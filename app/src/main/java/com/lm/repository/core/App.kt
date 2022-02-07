package com.lm.repository.core

import android.app.Application
import com.lm.repository.MainActivity
import com.lm.repository.RegistrationActivity
import com.lm.repository.di.app.components.DaggerAppComponent
import com.lm.repository.di.registration.components.DaggerRegComponent
import com.lm.repository.di.registration.components.RegComponent

class App : Application() {

    val appComponent by lazy { DaggerAppComponent.builder().application(this)
        .create() }

}

val MainActivity.appComponent get() = (applicationContext as App).appComponent

