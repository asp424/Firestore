package com.lm.repository.ui.screens.onuserinfo

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lm.repository.MainActivity
import com.lm.repository.R
import com.lm.repository.data.models.FirePath
import com.lm.repository.di.MainDep.depends
import com.lm.repository.theme.back
import com.lm.repository.ui.cells.CollapsedHeader
import com.lm.repository.ui.cells.DatePicker
import com.lm.repository.ui.cells.TextAnim
import com.lm.repository.ui.cells.Visibility
import com.lm.repository.ui.navigator.Screens
import com.lm.repository.ui.navigator.screen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@OptIn(ExperimentalCoroutinesApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
@Composable
fun MyProfile() {
    depends.apply {
        mainViewModel.user().also { user ->
            var name by rememberSaveable { mutableStateOf(user.name) }
            var patr by rememberSaveable { mutableStateOf(user.patr) }
            var sName by rememberSaveable { mutableStateOf(user.sName) }
            var yo by rememberSaveable { mutableStateOf(user.yo) }
            var email by rememberSaveable { mutableStateOf(user.eMail) }
            var sex by rememberSaveable { mutableStateOf(user.sex) }
            var check1 by rememberSaveable { mutableStateOf(user.check1) }
            var check2 by rememberSaveable { mutableStateOf(user.check2) }
            var check3 by rememberSaveable { mutableStateOf(user.check3) }
            var check4 by rememberSaveable { mutableStateOf(user.check4) }
            var check5 by rememberSaveable { mutableStateOf(false) }
            val width = LocalConfiguration.current.screenWidthDp.dp / 2
            var visibleDP by remember {
                mutableStateOf(false)
            }
            var visibleButtonText by remember {
                mutableStateOf(true)
            }
            var visibleProgress by remember {
                mutableStateOf(false)
            }
            depends.sharedPrefProvider.apply {
                CollapsedHeader(header = "ВАШИ ДАННЫЕ") {

                    Card(
                        elevation = 20.dp, modifier = Modifier
                            .width(LocalConfiguration.current.screenWidthDp.dp)
                            .fillMaxHeight()
                            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                    ) {
                        Column {
                            Column(
                                Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth(), horizontalAlignment = Alignment.End
                            ) {
                                Row(
                                    Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "Имя: ", color = Gray)
                                    Column(Modifier.padding(start = 10.dp)) {
                                        BasicTextField(
                                            value = name,
                                            onValueChange = { name = it },
                                            textStyle = TextStyle(fontSize = 18.sp),
                                            singleLine = true,
                                            modifier = Modifier.width(width)
                                        )
                                        Spacer(
                                            modifier = Modifier
                                                .width(width)
                                                .height(1.dp)
                                                .background(Gray)
                                        )
                                    }
                                }

                                Row(
                                    Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "Отчество: ", color = Gray)
                                    Column(Modifier.padding(start = 10.dp)) {
                                        BasicTextField(
                                            value = patr,
                                            onValueChange = { patr = it },
                                            textStyle = TextStyle(fontSize = 18.sp),
                                            singleLine = true,
                                            modifier = Modifier.width(width)
                                        )
                                        Spacer(
                                            modifier = Modifier
                                                .width(width)
                                                .height(1.dp)
                                                .background(Gray)
                                        )
                                    }
                                }

                                Row(
                                    Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "Фамилия: ", color = Gray)
                                    Column(Modifier.padding(start = 10.dp)) {
                                        BasicTextField(
                                            value = sName,
                                            onValueChange = { sName = it },
                                            textStyle = TextStyle(fontSize = 18.sp),
                                            singleLine = true,
                                            modifier = Modifier.width(width)
                                        )
                                        Spacer(
                                            modifier = Modifier
                                                .width(width)
                                                .height(1.dp)
                                                .background(Gray)
                                        )
                                    }
                                }

                                Row(
                                    Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "Дата рождения: ", color = Gray, fontSize = 11.sp)
                                    Column(Modifier.padding(start = 10.dp)) {
                                        BasicTextField(
                                            value = yo,
                                            onValueChange = { yo = it },
                                            textStyle = TextStyle(fontSize = 18.sp),
                                            singleLine = true,
                                            modifier = Modifier
                                                .width(width)
                                                .clickable {
                                                    visibleDP = true
                                                },
                                            readOnly = true,
                                            enabled = false
                                        )
                                        Spacer(
                                            modifier = Modifier
                                                .width(width)
                                                .height(1.dp)
                                                .background(Gray)
                                        )
                                    }
                                }

                                Row(
                                    Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "Email: ", color = Gray)
                                    Column(Modifier.padding(start = 10.dp)) {
                                        BasicTextField(
                                            value = email,
                                            onValueChange = { email = it },
                                            textStyle = TextStyle(fontSize = 18.sp),
                                            singleLine = true,
                                            modifier = Modifier.width(width)
                                        )
                                        Spacer(
                                            modifier = Modifier
                                                .width(width)
                                                .height(1.dp)
                                                .background(Gray)
                                        )
                                    }
                                }

                                Row(
                                    Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "Пол: ", color = Gray)
                                    Column(Modifier.padding(start = 10.dp)) {
                                        BasicTextField(
                                            value = sex,
                                            onValueChange = { sex = it },
                                            textStyle = TextStyle(fontSize = 18.sp),
                                            singleLine = true,
                                            modifier = Modifier.width(width)
                                        )
                                        Spacer(
                                            modifier = Modifier
                                                .width(width)
                                                .height(1.dp)
                                                .background(Gray)
                                        )
                                    }
                                }
                            }
                            Column(Modifier.padding(bottom = 10.dp)) {
                                Text(
                                    text = "Выберите, что, для Вас, может быть интересно в наших ресторанах:",
                                    fontStyle = FontStyle.Italic, color = Gray,
                                    modifier = Modifier.padding(end = 4.dp, start = 20.dp)
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(start = 30.dp)
                                        .clickable {
                                            check1 = !check1
                                        }
                                ) {
                                    Checkbox(checked = check1, onCheckedChange = { check1 = it })
                                    Text(text = "Караоке")
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(start = 30.dp)
                                        .clickable {
                                            check2 = !check2
                                        }
                                ) {
                                    Checkbox(checked = check2, onCheckedChange = { check2 = it })
                                    Text(text = "Мастер-классы")
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(start = 30.dp)
                                        .clickable {
                                            check3 = !check3
                                        }
                                ) {
                                    Checkbox(checked = check3, onCheckedChange = { check3 = it })
                                    Text(text = "Новинки меню")
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(start = 30.dp)
                                        .clickable {
                                            check4 = !check4
                                        }
                                ) {
                                    Checkbox(checked = check4, onCheckedChange = { check4 = it })
                                    Text(text = "Праздники")
                                }
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(Gray)
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(start = 20.dp, top = 10.dp)
                                        .clickable {
                                            check5 = !check5
                                        }
                                ) {
                                    Checkbox(
                                        checked = check5,
                                        onCheckedChange = { check5 = it }
                                    )
                                    Column {
                                        Text(text = "Подтверждаю, что ознакомлен и согласен с условиями")
                                        Text(text = "Политики конфиденциальности", color = Red)
                                    }
                                }
                                depends.sharedPrefProvider.apply {
                                    (LocalContext.current as MainActivity).also { act ->
                                        Box(
                                            modifier = Modifier
                                                .height(screenHeight / 8)
                                                .align(
                                                    Alignment.CenterHorizontally
                                                )
                                        ) {
                                            Visibility(visible = visibleProgress) {

                                                TextAnim(text = "Сохранение...")

                                            }
                                            Visibility(visible = visibleButtonText) {
                                                (LocalContext.current as MainActivity).apply {
                                                    stringResource(id = R.string.error_name).also {
                                                        Button(
                                                            onClick = {
                                                                if (!mainViewModel.internetStatus()) {
                                                                    visibleButtonText = false
                                                                    visibleProgress = true
                                                                    if (name.isNotEmpty()) {
                                                                        mainViewModel.user()
                                                                            .also { u ->
                                                                                u.name = name
                                                                                u.sName = sName
                                                                                u.patr = patr
                                                                                u.yo = yo
                                                                                u.eMail = email
                                                                                u.sex = sex
                                                                                u.check1 = check1
                                                                                u.check2 = check2
                                                                                u.check3 = check3
                                                                                u.check4 = check4
                                                                            }
                                                                        mainViewModel.putDataToDocument(
                                                                            FirePath(
                                                                                "users", read()
                                                                            ), hashMapOf(
                                                                                "name" to name,
                                                                                "patr" to patr,
                                                                                "sName" to sName,
                                                                                "yo" to yo,
                                                                                "email" to email,
                                                                                "sex" to sex,
                                                                                "check1" to check1.toString(),
                                                                                "check2" to check2.toString(),
                                                                                "check3" to check3.toString(),
                                                                                "check4" to check4.toString()
                                                                            )
                                                                        ) {
                                                                            visibleButtonText = true
                                                                            visibleProgress = false
                                                                            coroutine.launch {
                                                                                navController.navigate(
                                                                                    "UserInfo"
                                                                                )
                                                                            }
                                                                        }
                                                                    } else Toast.makeText(
                                                                        act,
                                                                        it,
                                                                        Toast.LENGTH_LONG
                                                                    ).show()
                                                                } else Toast.makeText(
                                                                    this,
                                                                    "Отсутствует интернет",
                                                                    Toast.LENGTH_LONG
                                                                ).show()
                                                            }, modifier = Modifier
                                                                .padding(20.dp)
                                                                .fillMaxWidth()
                                                                .height(LocalConfiguration.current.screenHeightDp.dp / 14),
                                                            colors = ButtonDefaults.buttonColors(
                                                                backgroundColor = back
                                                            ),
                                                            enabled = check5
                                                        ) {

                                                            Text(
                                                                text = "Сохранить",
                                                                fontSize = 16.sp
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            BackHandler {
                                navController.navigate(screen(Screens.UserInfo))
                            }
                        }

                    }
                }
                    Visibility(visible = visibleDP) {
                        DatePicker { bool, res ->
                            visibleDP = false
                            if (bool) {
                                yo = res
                            }
                        }
                    }
                }
            }
        }
    }

