package com.lm.repository.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lm.repository.core.Resource
import com.lm.repository.core.SharedPrefProvider
import com.lm.repository.data.models.FirePath
import com.lm.repository.data.repository.registration.RegResponse
import com.lm.repository.theme.Purple700
import com.lm.repository.theme.back
import com.lm.repository.ui.cells.ColumnFMS
import com.lm.repository.ui.cells.CustomTextField
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

    ColumnFMS(vertArr = Arrangement.Center) {

        Card(
            modifier = Modifier
                .size(LocalConfiguration.current.screenWidthDp.dp - 50.dp),
            backgroundColor = Gray
        ) {
            ColumnFMS {
                Text(
                    text = text1,
                    fontWeight = FontWeight.Bold, color = Yellow)
                Visibility(visible = visiblePhone) {
                    CustomTextField(
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Phone,
                                null,
                                tint = LocalContentColor.current.copy(alpha = 0.3f)
                            )
                        },
                        trailingIcon = null,
                        modifier = Modifier
                            .background(
                                MaterialTheme.colors.surface,
                                RoundedCornerShape(percent = 20)
                            )
                            .padding(4.dp)
                            .height(LocalConfiguration.current.screenHeightDp.dp / 18),
                        fontSize = 16.sp,
                        placeholderText = "Телефон"
                    ){
                        phone = it
                    }
                }

                Visibility(visible = visibleCode) {
                    CustomTextField(
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Code,
                                null,
                                tint = LocalContentColor.current.copy(alpha = 0.3f)
                            )
                        },
                        trailingIcon = null,
                        modifier = Modifier
                            .background(
                                MaterialTheme.colors.surface,
                                RoundedCornerShape(percent = 20)
                            )
                            .padding(4.dp)
                            .height(LocalConfiguration.current.screenHeightDp.dp / 18),
                        fontSize = 16.sp,
                        placeholderText = "SMS код"
                    ){
                        code = it
                    }
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
                            modifier = Modifier
                                .width(LocalConfiguration.current.screenWidthDp.dp / 2),
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
                        phone.replace(" ", "").apply {
                            when (buttonText) {
                                "ВОЙТИ" ->
                                    if (length == 12 && startsWith("+"))
                                        coroutine.launch {
                                            error = ""
                                            keyboardActions?.hide()
                                            visible = true
                                            viewModel.startAuth(phone, 60L).collect {
                                                when (it) {
                                                    is RegResponse.OnSuccess -> {
                                                        it.uid?.let { it1 ->
                                                            sharedPrefProvider.save(it1)
                                                        }
                                                        mainViewModel.dataFromDocumentV(
                                                            FirePath(
                                                                "admin", "phones"
                                                            )
                                                        )
                                                            .collect { map ->
                                                                if (map is Resource.Success) {
                                                                    if (map.data?.data?.containsValue(
                                                                            this@apply
                                                                        ) == true && map.data.data!!.getValue(
                                                                            checkNotNull(it.uid)
                                                                        ) == this@apply
                                                                    ) {
                                                                        mainViewModel.dataFromDocumentV(
                                                                            FirePath(
                                                                                "users",
                                                                                sharedPrefProvider.read()
                                                                            )
                                                                        ).collect { a ->
                                                                            if (a is Resource.Success) {
                                                                                visible = false
                                                                                a.data?.data?.get("name")
                                                                                    ?.let { it1 ->
                                                                                        regCallback(
                                                                                            it,
                                                                                            it1.toString()
                                                                                        )
                                                                                    }
                                                                            }
                                                                        }
                                                                    } else {
                                                                        mainViewModel.putDataToDocument(
                                                                            FirePath(
                                                                                "users",
                                                                                sharedPrefProvider.read()
                                                                            ),
                                                                            hashMapOf(
                                                                                "phone" to this@apply,
                                                                                "uid" to sharedPrefProvider.read()
                                                                            )
                                                                        ) {
                                                                            mainViewModel.putDataToDocument(
                                                                                FirePath(
                                                                                    "admin",
                                                                                    "phones"
                                                                                ),
                                                                                hashMapOf(this@apply to sharedPrefProvider.read())
                                                                            ) {

                                                                                text1 =
                                                                                    "ДАВАЙТЕ ПОЗНАКОМИМСЯ"
                                                                                buttonText =
                                                                                    "ОТПРАВИТЬ"
                                                                                visible = false
                                                                                visibleCode = false
                                                                                visiblePhone = false
                                                                                visibleName = true
                                                                            }
                                                                        }
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
                                                            it.uid?.let { it1 ->
                                                                sharedPrefProvider.save(it1)
                                                            }
                                                            mainViewModel.dataFromDocumentV(
                                                                FirePath(
                                                                    "admin",
                                                                    "phones"
                                                                )
                                                            ).collect { map ->
                                                                if (map is Resource.Success) {
                                                                    if (map.data?.data?.containsKey(
                                                                            this@apply
                                                                        ) == true
                                                                    ) {
                                                                        mainViewModel.dataFromDocumentV(
                                                                            FirePath(
                                                                                "users",
                                                                                sharedPrefProvider.read()
                                                                            )
                                                                        ).collect { a ->
                                                                            if (a is Resource.Success) {
                                                                                visible = false
                                                                                a.data?.data?.get("name")
                                                                                    ?.let { it1 ->
                                                                                        regCallback(
                                                                                            it,
                                                                                            it1.toString()
                                                                                        )
                                                                                    }
                                                                            }
                                                                        }
                                                                    } else {
                                                                        mainViewModel.putDataToDocument(
                                                                            FirePath(
                                                                                "users",
                                                                                sharedPrefProvider.read()
                                                                            ),
                                                                            hashMapOf(
                                                                                "phone" to this@apply,
                                                                                "uid" to sharedPrefProvider.read()
                                                                            )
                                                                        ) {
                                                                            mainViewModel.putDataToDocument(
                                                                                FirePath(
                                                                                    "admin",
                                                                                    "phones"
                                                                                ),
                                                                                hashMapOf(this@apply to sharedPrefProvider.read())
                                                                            ) {

                                                                                text1 =
                                                                                    "ДАВАЙТЕ ПОЗНАКОМИМСЯ"
                                                                                buttonText =
                                                                                    "ОТПРАВИТЬ"
                                                                                visible = false
                                                                                visibleCode = false
                                                                                visiblePhone = false
                                                                                visibleName = true
                                                                            }
                                                                        }
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
                                        mainViewModel.putDataToDocument(
                                            FirePath("users", sharedPrefProvider.read()),
                                            hashMapOf(
                                                "name" to name,
                                                "email" to email,
                                                "uid" to sharedPrefProvider.read()
                                            )
                                        ) {
                                            regCallback(
                                                RegResponse.OnSuccess(sharedPrefProvider.read()),
                                                ""
                                            )
                                        }
                                    }
                            }
                        }
                    },
                    Modifier
                        .width(LocalConfiguration.current.screenWidthDp.dp - 100.dp)
                        .height(LocalConfiguration.current.screenHeightDp.dp / 18),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = back, contentColor = Black
                    ), shape = RoundedCornerShape(9.dp)
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
