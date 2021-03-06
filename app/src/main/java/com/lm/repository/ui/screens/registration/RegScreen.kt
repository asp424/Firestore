package com.lm.repository.ui.screens.registration

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lm.repository.data.repository.registration.RegResponse
import com.lm.repository.di.MainDep.depends
import com.lm.repository.di.RegDep.dependsReg
import com.lm.repository.di.RegDependencies
import com.lm.repository.theme.back
import com.lm.repository.ui.cells.ColumnFMS
import com.lm.repository.ui.cells.CustomTextField
import com.lm.repository.ui.cells.Visibility
import com.lm.repository.ui.screens.registration.utils.onSuccess
import com.lm.repository.ui.screens.registration.utils.sendData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalCoroutinesApi::class, androidx.compose.animation.ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class,
    androidx.compose.ui.ExperimentalComposeUiApi::class
)
@Composable
fun RegScreen(
    regCallback: (RegResponse, String) -> Unit
) {
    var text1 by rememberSaveable { mutableStateOf("????????????????????\n????????????????????????????") }
    var phone by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var visibleProgress by remember { mutableStateOf(false) }
    var visibleError by remember { mutableStateOf(false) }
    var code by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf("") }
    var buttonText by remember { mutableStateOf("??????????") }
    var visibleCode by remember { mutableStateOf(false) }
    var visiblePhone by remember { mutableStateOf(true) }
    var visibleName by remember { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope()
    val keyboardActions = LocalSoftwareKeyboardController.current

    ColumnFMS(vertArr = Arrangement.Center) {
        Card(
            modifier = Modifier.size(depends.screenWidth - 50.dp), backgroundColor = Gray
        ) {
            ColumnFMS {
                Text(
                    text = text1,
                    fontWeight = FontWeight.Bold, color = Yellow, textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                Box {
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
                            fontSize = 16.sp,
                            placeholderText = "??????????????",
                            modifier = Modifier,
                            keyboardOptions = KeyboardType.Phone
                        )
                        {
                            phone = it
                            error = ""
                        }
                    }

                    Visibility(visible = visibleCode) {
                        BasicTextField(
                            value = code,
                            onValueChange = {
                                code = it
                                error = ""
                            },
                            singleLine = true,
                            cursorBrush = SolidColor(MaterialTheme.colors.primary),
                            textStyle = LocalTextStyle.current.copy(
                                color = MaterialTheme.colors.onSurface,
                                fontSize = 16.sp
                            ),
                            decorationBox = { innerTextField ->
                                Row(
                                    Modifier
                                        .background(
                                            MaterialTheme.colors.surface,
                                            RoundedCornerShape(percent = 20)
                                        )
                                        .padding(4.dp)
                                        .height(depends.screenHeight / 18)
                                        .background(
                                            MaterialTheme.colors.surface,
                                            MaterialTheme.shapes.small,
                                        )
                                        .width(depends.screenWidth - 110.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Filled.Code,
                                        null,
                                        tint = LocalContentColor.current.copy(alpha = 0.3f)
                                    )
                                    Box(Modifier.weight(1f)) {
                                        if (code.isEmpty()) Text(
                                            "SMS ??????",
                                            style = LocalTextStyle.current.copy(
                                                color = MaterialTheme.colors.onSurface.copy(
                                                    alpha = 0.3f
                                                ),
                                                fontSize = 16.sp
                                            )
                                        )
                                        innerTextField()
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        )
                    }
                }

                Visibility(visible = visibleName) {
                    Column {
                        CustomTextField(
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Face,
                                    null,
                                    tint = LocalContentColor.current.copy(alpha = 0.3f)
                                )
                            },
                            trailingIcon = null,
                            fontSize = 16.sp,
                            placeholderText = "??????",
                            modifier = Modifier,
                            keyboardOptions = KeyboardType.Text
                        )
                        {
                            name = it
                            error = ""
                        }

                        CustomTextField(
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Email,
                                    null,
                                    tint = LocalContentColor.current.copy(alpha = 0.3f)
                                )
                            },
                            trailingIcon = null,
                            fontSize = 16.sp,
                            placeholderText = "Email",
                            modifier = Modifier.padding(top = 10.dp),
                            keyboardOptions = KeyboardType.Email
                        )
                        {
                            error = ""
                            email = it
                        }
                    }
                }
                depends.mainViewModel.also { mVm ->
                    RegDependencies {
                        dependsReg.regViewModel.also { rVm ->
                            depends.sharedPrefProvider.also { shP ->
                                (LocalContext.current).also { con ->
                                    Button(
                                        onClick = {
                                            if (!mVm.internetStatus())
                                                phone.replace(" ", "").also { phoneN ->
                                                    when (buttonText) {
                                                        "??????????" ->
                                                            if (phoneN.length == 12 && phoneN.startsWith(
                                                                    "+"
                                                                )
                                                            )
                                                                coroutine.launch {
                                                                    error = ""
                                                                    keyboardActions?.hide()
                                                                    visibleProgress = true
                                                                    rVm.startAuth(phone, 60L)
                                                                        .collect {
                                                                            when (it) {
                                                                                is RegResponse.OnSuccess -> {
                                                                                    onSuccess(mVm,
                                                                                        shP,
                                                                                        it,
                                                                                        phoneN,
                                                                                        regCallback = { res, name ->
                                                                                            visibleProgress =
                                                                                                false
                                                                                            text1 =
                                                                                                "????????????????????\n????????????????????????????"
                                                                                            buttonText =
                                                                                                "??????????"
                                                                                            visibleProgress =
                                                                                                false
                                                                                            visibleCode =
                                                                                                false
                                                                                            visiblePhone =
                                                                                                true
                                                                                            visibleName =
                                                                                                false
                                                                                            regCallback(
                                                                                                res,
                                                                                                name
                                                                                            )
                                                                                        },
                                                                                        newCallback = {
                                                                                            text1 =
                                                                                                "??????????????\n????????????????????????"
                                                                                            buttonText =
                                                                                                "??????????????????"
                                                                                            visibleProgress =
                                                                                                false
                                                                                            visibleCode =
                                                                                                false
                                                                                            visiblePhone =
                                                                                                false
                                                                                            visibleName =
                                                                                                true
                                                                                        }
                                                                                    )
                                                                                }

                                                                                is RegResponse.SmsCode -> {
                                                                                    code =
                                                                                        it.smsCode.toString()
                                                                                    visibleProgress =
                                                                                        true
                                                                                }

                                                                                is RegResponse.OnError -> {
                                                                                    error =
                                                                                        it.exception.toString()
                                                                                    visibleProgress =
                                                                                        false
                                                                                    visibleError =
                                                                                        true
                                                                                }

                                                                                is RegResponse.RegId -> {
                                                                                    visibleProgress =
                                                                                        false
                                                                                    buttonText =
                                                                                        "?????????????????? ??????"
                                                                                    visiblePhone =
                                                                                        false
                                                                                    visibleCode =
                                                                                        true
                                                                                }
                                                                                else -> Unit
                                                                            }
                                                                        }
                                                                }
                                                            else {
                                                                keyboardActions?.hide()
                                                                error =
                                                                    "???????????????????? ?????????? ???????????? ???????????????????? ?? '+7' ?? ???????????????? ???? 12 - ???? ????????????????"
                                                                visibleError = true
                                                            }
                                                        "?????????????????? ??????" ->
                                                            if (code.replace(" ", "").length == 6)
                                                                coroutine.launch {
                                                                    error = ""
                                                                    keyboardActions?.hide()
                                                                    visibleProgress = true
                                                                    rVm.authWithCode(
                                                                        shP.read(), code
                                                                    ).collect {
                                                                        when (it) {
                                                                            is RegResponse.OnSuccess -> {
                                                                                onSuccess(mVm,
                                                                                    shP,
                                                                                    it,
                                                                                    phoneN,
                                                                                    regCallback = { res, name ->
                                                                                        visibleProgress =
                                                                                            false
                                                                                        text1 =
                                                                                            "????????????????????\n" +
                                                                                                    "????????????????????????????"
                                                                                        buttonText =
                                                                                            "??????????"
                                                                                        visibleProgress =
                                                                                            false
                                                                                        visibleCode =
                                                                                            false
                                                                                        visiblePhone =
                                                                                            true
                                                                                        visibleName =
                                                                                            false
                                                                                        regCallback(
                                                                                            res,
                                                                                            name
                                                                                        )
                                                                                        code = ""
                                                                                    },
                                                                                    newCallback = {
                                                                                        text1 =
                                                                                            "??????????????\n" +
                                                                                                    "????????????????????????"
                                                                                        buttonText =
                                                                                            "??????????????????"
                                                                                        visibleProgress =
                                                                                            false
                                                                                        visibleCode =
                                                                                            false
                                                                                        visiblePhone =
                                                                                            false
                                                                                        visibleName =
                                                                                            true
                                                                                        code = ""
                                                                                    }
                                                                                )
                                                                            }
                                                                            is RegResponse.OnError -> {
                                                                                error =
                                                                                    it.exception.toString()
                                                                                visibleProgress =
                                                                                    false
                                                                                visibleError = true
                                                                            }
                                                                            else -> Unit
                                                                        }
                                                                    }
                                                                }
                                                            else {
                                                                keyboardActions?.hide()
                                                                error =
                                                                    "?????? ???????????? ???????? ????????????????????????"
                                                                visibleError = true
                                                            }
                                                        "??????????????????" -> if (name.isNotEmpty())
                                                            coroutine.launch {
                                                                visibleProgress = true
                                                                keyboardActions?.hide()
                                                                error = ""
                                                                sendData(mVm, shP, name, email) {
                                                                    regCallback(
                                                                        RegResponse.OnSuccess(shP.read()),
                                                                        ""
                                                                    )
                                                                    text1 = "????????????????????\n" +
                                                                            "????????????????????????????"
                                                                    buttonText = "??????????"
                                                                    visibleProgress = false
                                                                    visibleCode = false
                                                                    visiblePhone = true
                                                                    visibleName = false
                                                                }
                                                            }
                                                    }

                                                }
                                            else Toast.makeText(
                                                con,
                                                "?????????????????????? ????????????????",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        },
                                        Modifier
                                            .width(depends.screenWidth - 100.dp)
                                            .height(depends.screenHeight / 13)
                                            .padding(top = 10.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = back, contentColor = Black
                                        ), shape = RoundedCornerShape(9.dp)
                                    ) { Text(text = buttonText) }
                                }
                                Visibility(visible = visibleProgress) {
                                    CircularProgressIndicator(
                                        color = Yellow,
                                        modifier = Modifier.padding(top = depends.screenHeight / 20)
                                    )
                                }
                            }

                            Visibility(visible = visibleError) {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = error,
                                        Modifier.padding(10.dp),
                                        textAlign = TextAlign.Center,
                                        color = Red
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}




