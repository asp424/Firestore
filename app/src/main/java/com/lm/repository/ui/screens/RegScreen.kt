package com.lm.repository.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.lm.repository.core.Resource
import com.lm.repository.core.SharedPrefProvider
import com.lm.repository.data.models.FirePath
import com.lm.repository.data.repository.registration.RegResponse
import com.lm.repository.theme.Purple700
import com.lm.repository.ui.cells.ColumnFMS
import com.lm.repository.ui.cells.Visibility
import com.lm.repository.ui.viewmodels.MainViewModel
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
    mainViewModel: MainViewModel,
    sharedPrefProvider: SharedPrefProvider,
    regCallback: (RegResponse, String) -> Unit
) {
    var text1 by rememberSaveable { mutableStateOf("ВХОД") }
    var text2 by rememberSaveable { mutableStateOf("ПО НОМЕРУ ТЕЛЕФОНА") }
    var phone by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }
    var visibleError by remember { mutableStateOf(false) }
    var code by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf("") }
    var buttonText by remember { mutableStateOf("ВОЙТИ") }
    var visibleCode by remember { mutableStateOf(false) }
    var visiblePhone by remember { mutableStateOf(true) }
    var visibleName by remember { mutableStateOf(false) }
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
                    text = text1, modifier = Modifier.padding(bottom = 2.dp),
                    fontWeight = FontWeight.Bold, color = Yellow
                )

                Text(
                    text = text2, modifier = Modifier.padding(bottom = 25.dp),
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

                Visibility(visible = visibleName) {
                    Column {
                        OutlinedTextField(
                            value = name,
                            onValueChange = {
                                error = ""
                                visibleError = false
                                name = it
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                backgroundColor = White,
                                cursorColor = Green,
                                focusedBorderColor = Purple700
                            ),
                            modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp / 2).padding(bottom = 10.dp),
                            textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 18.sp),
                            placeholder = {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(text = "Имя", textAlign = TextAlign.Center)
                                }
                            }
                        )

                        OutlinedTextField(
                            value = email,
                            onValueChange = {
                                error = ""
                                visibleError = false
                                email = it
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                backgroundColor = White,
                                cursorColor = Green,
                                focusedBorderColor = Purple700
                            ),
                            modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp / 2),
                            textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 18.sp),
                            placeholder = {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(text = "Email", textAlign = TextAlign.Center)
                                }
                            }
                        )
                    }
                }

                Button(
                    onClick = {
                        when (buttonText) {
                            "ВОЙТИ" ->
                                if (phone.replace(" ", "").length == 12)
                                    coroutine.launch {
                                        error = ""
                                        keyboardActions?.hide()
                                        visible = true
                                        viewModel.startAuth(phone, 60L).collect {
                                            when (it) {
                                                is RegResponse.OnSuccess -> {
                                                    mainViewModel.dataFromDocumentV(
                                                        FirePath("admin", "phones"
                                                        )
                                                    )
                                                        .collect { map ->
                                                            if (map is Resource.Success) {
                                                                if (map.data?.data?.containsValue(
                                                                        phone.replace(" ", "")
                                                                    ) == true&& map.data.data!!.getValue(
                                                                        checkNotNull(it.uid)) == phone.replace(" ",
                                                                        "")
                                                                ) {
                                                                    mainViewModel.dataFromDocumentV(
                                                                        FirePath(
                                                                            "users", phone)
                                                                    ).collect { a ->
                                                                        if ( a is Resource.Success) {
                                                                            visible = false
                                                                            a.data?.data?.get("name")
                                                                                ?.let { it1 -> regCallback(it, it1.toString()) }
                                                                        }
                                                                    }
                                                                } else {
                                                                    it.uid?.let { it1 ->
                                                                        sharedPrefProvider.save(
                                                                            it1
                                                                        )
                                                                    }
                                                                    text1 = ""
                                                                    text2 = "ДАВАЙТЕ ПОЗНАКОМИМСЯ"
                                                                    buttonText = "ОТПРАВИТЬ"
                                                                    visible = false
                                                                    visibleCode = false
                                                                    visiblePhone = false
                                                                    visibleName = true
                                                                }
                                                            }
                                                        }
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
                                    error =
                                        "Телефонный номер должен начинаться с '+7' и состоять из 12 - ти символов"
                                    visibleError = true
                                }
                            "ОТПРАВИТЬ КОД" ->
                                if (code.replace(" ", "").length == 6)
                                    coroutine.launch {
                                        error = ""
                                        keyboardActions?.hide()
                                        visible = true
                                        viewModel.authWithCode(sharedPrefProvider.read(), code)
                                            .collect {
                                                when (it) {
                                                    is RegResponse.OnSuccess -> {
                                                        mainViewModel.dataFromDocumentV(
                                                            FirePath(
                                                                "admin",
                                                                "phones"
                                                            )
                                                        ).collect { map ->
                                                            if (map is Resource.Success) {
                                                                if (map.data?.data?.containsValue(
                                                                        phone.replace(" ", "")
                                                                    ) == true && map.data.data!!.getValue(
                                                                        checkNotNull(it.uid)) == phone.replace(" ",
                                                                        "")
                                                                ) {
                                                                    mainViewModel.dataFromDocumentV(
                                                                        FirePath(
                                                                            "users",
                                                                            phone
                                                                        )
                                                                    ).collect { a ->
                                                                        if ( a is Resource.Success) {
                                                                            visible = false
                                                                            a.data?.data?.get("name")
                                                                                ?.let { it1 -> regCallback(it, it1.toString()) }
                                                                        }
                                                                    }
                                                                } else {
                                                                    it.uid?.let { it1 ->
                                                                        sharedPrefProvider.save(it1)
                                                                    }
                                                                    text1 = ""
                                                                    text2 = "ДАВАЙТЕ ПОЗНАКОМИМСЯ"
                                                                    buttonText = "ОТПРАВИТЬ"
                                                                    visible = false
                                                                    visibleCode = false
                                                                    visiblePhone = false
                                                                    visibleName = true
                                                                }
                                                            }
                                                        }
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
                            "ОТПРАВИТЬ" -> if (name.isNotEmpty())
                                coroutine.launch {
                                    error = ""
                                    mainViewModel.putDataToDocument(FirePath("admin", "phones"),
                                        hashMapOf(sharedPrefProvider.read() to phone)){
                                        mainViewModel.putDataToDocument(
                                            FirePath("users", phone),
                                            hashMapOf(
                                                "name" to name,
                                                "email" to email,
                                                "uid" to sharedPrefProvider.read()
                                            )
                                        ) {
                                            regCallback(RegResponse.OnSuccess(sharedPrefProvider.read()), "")
                                        }
                                    }
                                }
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
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = error, Modifier.padding(10.dp), textAlign = TextAlign.Center)
            }
        }
    }
}





