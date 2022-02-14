package com.lm.repository.data.repository.internet

import android.net.ConnectivityManager
import android.net.Network

class InternetCallback(private val status: (Boolean) -> Unit): ConnectivityManager.NetworkCallback() {

    override fun onAvailable(network: Network) {
        status(false)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        status(true)
    }
}
