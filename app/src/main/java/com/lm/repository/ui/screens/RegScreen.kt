package com.lm.repository.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lm.repository.core.SharedPrefProvider
import com.lm.repository.data.repository.registration.RegResponse
import com.lm.repository.theme.Purple700
import com.lm.repository.ui.cells.ColumnFMS
import com.lm.repository.ui.cells.Visibility
import com.lm.repository.ui.viewmodels.RegViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalCoroutinesApi::class, androidx.compose.animation.ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class,
    androidx.compose.ui.ExperimentalComposeUiApi::class
)
@Composable
fun RegScreen(
    viewModel: RegViewModel,
    sharedPrefProvider: SharedPrefProvider,
    regCallback: (RegResponse) -> Unit
) {
    var phone by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }
    var visibleError by remember { mutableStateOf(false) }
    var code by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var buttonText by remember { mutableStateOf("ВОЙТИ") }
    var visibleCode by remember { mutableStateOf(false) }
    var visiblePhone by remember { mutableStateOf(true) }
    val coroutine = rememberCoroutineScope()
    val keyboardActions = LocalSoftwareKeyboardController.current

    ColumnFMS(vertArr = Arrangement.Top) {
        Icon(
            Icons.Default.Phone, contentDescription = null, modifier = Modifier
                .size(160.dp)
                .padding(top = 40.dp)
        )
        Card(
            modifier = Modifier
                .size(LocalConfiguration.current.screenWidthDp.dp - 50.dp)
                .padding(top = 40.dp),
            backgroundColor = Purple700
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "ВХОД", modifier = Modifier.padding(bottom = 2.dp),
                    fontWeight = FontWeight.Bold, color = Yellow
                )

                Text(
                    text = "ПО НОМЕРУ ТЕЛЕФОНА", modifier = Modifier.padding(bottom = 25.dp),
                    fontWeight = FontWeight.Bold, color = Yellow
                )

                Visibility(visible = visiblePhone) {
                    OutlinedTextField(
                        value = phone,
                        onValueChange = {
                            error = ""
                            visibleError = false
                            phone = it
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = White,
                            cursorColor = Green,
                            focusedBorderColor = Purple700
                        ),
                        modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp / 2),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 18.sp),
                        placeholder = {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Телефон", textAlign = TextAlign.Center)
                            }
                        }
                    )
                }

                Visibility(visible = visibleCode) {
                    OutlinedTextField(
                        value = code,
                        onValueChange = {
                            error = ""
                            visibleError = false
                            code = it
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = White,
                            cursorColor = Green,
                            focusedBorderColor = Purple700
                        ),
                        modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp / 2),
                        textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 18.sp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        placeholder = {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = "SMS код", textAlign = TextAlign.Center)
                            }
                        }
                    )
                }

                Button(
                    onClick = {
                        if (buttonText == "ВОЙТИ")
                            if (phone.replace(" ", "").length == 12)
                                coroutine.launch {
                                    keyboardActions?.hide()
                                    visible = true
                                    viewModel.startAuth(phone, 60L).collect {
                                        when (it) {
                                            is RegResponse.OnSuccess -> {
                                                visible = false
                                                regCallback(it)
                                            }

                                            is RegResponse.SmsCode -> {
                                                code = it.smsCode.toString()
                                                visible = true
                                            }

                                            is RegResponse.OnError -> {
                                                error = it.exception.toString()
                                                visible = false
                                                visibleError = true
                                            }

                                            is RegResponse.RegId -> {
                                                visible = false
                                                buttonText = "ОТПРАВИТЬ КОД"
                                                visiblePhone = false
                                                visibleCode = true
                                            }
                                            else -> Unit
                                        }
                                    }
                                }
                            else {
                                keyboardActions?.hide()
                                error = "Телефонный номер должен начинаться с '+7' и состоять из 12 - ти символов"
                                visibleError = true
                            }
                        else if (code.replace(" ", "").length == 6)
                            coroutine.launch {
                                keyboardActions?.hide()
                                visible = true
                                viewModel.authWithCode(sharedPrefProvider.read(), code)
                                    .collect {
                                        when (it) {
                                            is RegResponse.OnSuccess -> {
                                                visible = false
                                                regCallback(it)
                                            }

                                            is RegResponse.OnError -> {
                                                error = it.exception.toString()
                                                visible = false
                                                visibleError = true
                                            }
                                            else -> Unit
                                        }
                                    }
                            }
                        else {
                            keyboardActions?.hide()
                            error = "Код должен быть шестизначным"
                            visibleError = true
                        }
                    },
                    Modifier
                        .width(LocalConfiguration.current.screenWidthDp.dp / 2)
                        .height(66.dp)
                        .padding(top = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = White, contentColor = Black
                    )
                ) {
                    Text(text = buttonText)
                }
                Visibility(visible = visible) {
                    CircularProgressIndicator(
                        color = Yellow,
                        modifier = Modifier.padding(top = 10.dp, bottom = 3.dp)
                    )
                }
            }
        }
        Visibility(visible = visibleError) {
            Text(text = error, Modifier.padding(10.dp))
        }
    }
}





