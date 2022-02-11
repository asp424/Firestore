package com.lm.repository.ui.screens.registration.utils

import com.lm.repository.core.Resource
import com.lm.repository.core.SharedPrefProvider
import com.lm.repository.data.models.FirePath
import com.lm.repository.data.repository.registration.RegResponse
import com.lm.repository.ui.viewmodels.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun onSuccess(
    mainViewModel: MainViewModel,
    sharedPrefProvider: SharedPrefProvider,
    regResponse: RegResponse.OnSuccess,
    phone: String,
    regCallback: (RegResponse, String) -> Unit,
    newCallback: () -> Unit,
) {
    regResponse.uid?.let { it1 -> sharedPrefProvider.save(it1) }
    mainViewModel.apply {
        FirePath("users", sharedPrefProvider.read()).also { users ->
            FirePath("admin", "phones").also { admin ->
                mainViewModel.dataFromDocumentV(admin).collect { map ->
                    if (map is Resource.Success) {
                        if (map.data?.data?.containsKey(phone) == true
                        ) {
                            dataFromDocumentV(users).collect { a ->
                                if (a is Resource.Success) {
                                    regCallback(
                                        regResponse,
                                        a.data?.data?.get("name")?.toString() ?: ""
                                    )
                                }
                            }
                        } else {
                            putDataToDocument(
                                users,
                                hashMapOf("phone" to phone, "uid" to sharedPrefProvider.read())
                            ) {
                                putDataToDocument(
                                    admin,
                                    hashMapOf(phone to sharedPrefProvider.read())
                                ) { newCallback() }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun sendData(
    mainViewModel: MainViewModel,
    sharedPrefProvider: SharedPrefProvider,
    name: String,
    email: String,
    onSuccess: () -> Unit
) {
    mainViewModel.putDataToDocument(
        FirePath("users", sharedPrefProvider.read()),
        hashMapOf("name" to name, "email" to email)
    ) {
    onSuccess()
    }
}