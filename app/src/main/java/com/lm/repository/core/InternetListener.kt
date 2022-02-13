package com.lm.repository.core

import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.*
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun InternetListener(callback: (Boolean) -> Unit) {
    object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) { callback(false) }

        override fun onLost(network: Network) {
            super.onLost(network)
            callback(true)
        }
    }.also { cal ->
        LocalContext.current.applicationContext.apply {
            (getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager).also {
                it.getNetworkCapabilities(it.activeNetwork).also { cap ->
                    if (cap != null) {
                        when {
                            cap.hasTransport(TRANSPORT_CELLULAR) -> { callback(false) }
                            cap.hasTransport(TRANSPORT_WIFI) -> { callback(false) }
                            cap.hasTransport(TRANSPORT_ETHERNET) -> { callback(false) }
                            else -> Unit
                        }
                    }
                   else callback(true)
                    it.registerDefaultNetworkCallback(cal)
                    DisposableEffect(true) {
                        onDispose { it.unregisterNetworkCallback(cal) }
                    }
                }
            }
        }
    }
}


