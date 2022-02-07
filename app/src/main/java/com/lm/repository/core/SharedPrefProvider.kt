package com.lm.repository.core

import android.content.SharedPreferences
import javax.inject.Inject

interface SharedPrefProvider {

    fun save(value: String)

    fun read(): String

    class Base @Inject constructor(private val sharedPreferences: SharedPreferences) :
        SharedPrefProvider {

        override fun save(value: String) = sharedPreferences.edit().putString("id", value).apply()

        override fun read(): String = sharedPreferences.getString("id", "")!!
    }
}