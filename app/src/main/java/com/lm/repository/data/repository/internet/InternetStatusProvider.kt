package com.lm.repository.data.repository.internet

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


interface InternetStatusProvider {

    suspend fun startInternetListener(connectivityManager: ConnectivityManager): Flow<Boolean>

    class Base : InternetStatusProvider {
        override suspend fun startInternetListener(connectivityManager: ConnectivityManager) =
            callbackFlow {
                connectivityManager.apply {
                    getNetworkCapabilities(activeNetwork).also { cap ->
                        if (cap != null) {
                            when {
                                cap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                                    trySendBlocking(false)
                                }
                                cap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                                    trySendBlocking(false)
                                }
                                cap.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                                    trySendBlocking(false)
                                }
                                else -> Unit
                            }
                        } else trySendBlocking(true)

                        InternetCallback {
                            trySendBlocking(it)
                        }.apply {
                            registerDefaultNetworkCallback(this)
                            awaitClose { unregisterNetworkCallback(this) }
                        }
                    }
                }
            }
    }
}