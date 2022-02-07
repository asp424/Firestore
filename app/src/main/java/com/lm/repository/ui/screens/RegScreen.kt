package com.lm.repository.ui.screens

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.lm.repository.MainActivity
import com.lm.repository.RegistrationActivity
import com.lm.repository.core.SharedPrefProvider
import com.lm.repository.data.repository.registration.RegResponse
import com.lm.repository.ui.cells.ColumnFMS
import com.lm.repository.ui.cells.Visibility
import com.lm.repository.ui.viewmodels.RegViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
@Composable
fun RegScreen(
    viewModel: RegViewModel,
    sharedPrefProvider: SharedPrefProvider,
    registrationActivity: RegistrationActivity
) {

    var phone by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    var buttonText by remember { mutableStateOf("Start auth") }
    var visibleCode by remember { mutableStateOf(false) }
    var visiblePhone by remember { mutableStateOf(true) }
    val coroutine = rememberCoroutineScope()

    ColumnFMS {
        Visibility(visible = visiblePhone) {
            OutlinedTextField(value = phone, onValueChange = {
                phone = it
            })
        }

        Visibility(visible = visibleCode) {
            OutlinedTextField(value = code, onValueChange = {
                code = it
            })
        }

        Text(text = id, modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))
        Button(onClick = {
            if (buttonText == "Start auth")
                coroutine.launch {
                    viewModel.startAuth(phone, 60L).collect {
                        with(it) {
                            when (this) {
                                is RegResponse.OnSuccess -> {
                                    Log.d("My", uid.toString())
                                    id = this.uid.toString()
                                }
                                is RegResponse.SmsCode -> {
                                    Log.d("My", smsCode.toString())
                                    id = this.smsCode.toString()
                                }
                                is RegResponse.OnError -> {
                                    Log.d("My", exception.toString())
                                    id = this.exception.toString()
                                }
                                is RegResponse.RegId -> {
                                    id = this.id.toString()
                                    buttonText = "Enter code"
                                    visiblePhone = false
                                    visibleCode = true
                                }
                                else -> {}
                            }
                        }
                    }
                }
            else coroutine.launch {
                viewModel.authWithCode(sharedPrefProvider.read(), code).collect {
                    when (it) {
                        is RegResponse.OnSuccess -> {
                            Log.d("My", it.uid.toString())
                            id = it.uid.toString()
                            registrationActivity.apply {
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }
                        }
                        is RegResponse.OnError -> {
                            Log.d("My", it.exception.toString())
                            id = it.exception.toString()
                        }
                        else -> Unit
                    }
                }
            }
        }) {
            Text(text = buttonText)
        }
    }
}