package com.lm.repository.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.lm.repository.core.Resource
import com.lm.repository.core.SharedPrefProvider
import com.lm.repository.data.models.FirePath
import com.lm.repository.theme.back
import com.lm.repository.ui.cells.ColumnFMS
import com.lm.repository.ui.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun MyProfile(
    mVm: MainViewModel,
    sharedPreferences: SharedPrefProvider,
    navController: NavHostController
) {
    var name by rememberSaveable { mutableStateOf("") }
    var patr by rememberSaveable { mutableStateOf("") }
    var sName by rememberSaveable { mutableStateOf("") }
    var yo by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var sex by rememberSaveable { mutableStateOf("") }
    var check1 by rememberSaveable { mutableStateOf(false) }
    var check2 by rememberSaveable { mutableStateOf(false) }
    var check3 by rememberSaveable { mutableStateOf(false) }
    var check4 by rememberSaveable { mutableStateOf(false) }
    var check5 by rememberSaveable { mutableStateOf(false) }
    val start by rememberSaveable { mutableStateOf(2) }

    LaunchedEffect(start) {
        mVm.dataFromDocumentV(
            FirePath(
                "users",
                sharedPreferences.read()
            )
        ).collect {
            if (it is Resource.Success) {
                if (it.data?.get("name") != null) name = it.data.get("name").toString()
                if (it.data?.get("patr") != null) patr = it.data.get("patr").toString()
                if (it.data?.get("sName") != null) sName = it.data.get("sName").toString()
                if (it.data?.get("yo") != null) yo = it.data.get("yo").toString()
                if (it.data?.get("email") != null) email = it.data.get("email").toString()
                if (it.data?.get("sex") != null) sex = it.data.get("sex").toString()
                if (it.data?.get("check1") != null) check1 = it.data.get("check1") == "true"
                if (it.data?.get("check2") != null) check2 = it.data.get("check2") == "true"
                if (it.data?.get("check3") != null) check3 = it.data.get("check3") == "true"
                if (it.data?.get("check4") != null) check4 = it.data.get("check4") == "true"
            }
        }
    }

    ColumnFMS(vertArr = Arrangement.Top, modifier = Modifier.padding(top = 30.dp)) {
        Text(text = "МОИ ДАННЫЕ", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(text = "XXXXXXXXXX")
        Card(
            elevation = 20.dp, modifier = Modifier
                .width(LocalConfiguration.current.screenWidthDp.dp)
                .fillMaxHeight()
                .padding(20.dp)
        ) {
            Column(Modifier.padding(10.dp)) {
                Row(Modifier.padding(10.dp)) {
                    Text(text = "Имя: ", color = Gray)
                    Column(Modifier.padding(start = 10.dp)) {
                        BasicTextField(
                            value = name, onValueChange = { name = it },
                            textStyle = TextStyle(fontSize = 18.sp)
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Gray)
                        )
                        Spacer(
                            modifier = Modifier
                                .width(120.dp)
                                .height(1.dp)
                                .background(Black)
                        )
                    }
                }

                Row(Modifier.padding(10.dp)) {
                    Text(text = "Отчество: ", color = Gray)
                    Column(Modifier.padding(start = 10.dp)) {
                        BasicTextField(
                            value = patr, onValueChange = { patr = it },
                            textStyle = TextStyle(fontSize = 18.sp)
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.Gray)
                        )
                        Spacer(
                            modifier = Modifier
                                .width(120.dp)
                                .height(1.dp)
                                .background(Black)
                        )
                    }
                }

                Row(Modifier.padding(10.dp)) {
                    Text(text = "Фамилия: ", color = Gray)
                    Column(Modifier.padding(start = 10.dp)) {
                        BasicTextField(
                            value = sName, onValueChange = { sName = it },
                            textStyle = TextStyle(fontSize = 18.sp)
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.Gray)
                        )
                        Spacer(
                            modifier = Modifier
                                .width(120.dp)
                                .height(1.dp)
                                .background(Black)
                        )
                    }
                }

                Row(Modifier.padding(10.dp)) {
                    Text(text = "Дата рождения: ", color = Gray)
                    Column(Modifier.padding(start = 10.dp)) {
                        BasicTextField(
                            value = yo, onValueChange = { yo = it },
                            textStyle = TextStyle(fontSize = 18.sp)
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.Gray)
                        )
                        Spacer(
                            modifier = Modifier
                                .width(120.dp)
                                .height(1.dp)
                                .background(Black)
                        )
                    }
                }

                Row(Modifier.padding(10.dp)) {
                    Text(text = "Email: ", color = Gray)
                    Column(Modifier.padding(start = 10.dp)) {
                        BasicTextField(
                            value = email, onValueChange = { email = it },
                            textStyle = TextStyle(fontSize = 18.sp)
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.Gray)
                        )
                        Spacer(
                            modifier = Modifier
                                .width(120.dp)
                                .height(1.dp)
                                .background(Black)
                        )
                    }
                }

                Row(Modifier.padding(10.dp)) {
                    Text(text = "Пол: ", color = Gray)
                    Column(Modifier.padding(start = 10.dp)) {
                        BasicTextField(
                            value = sex, onValueChange = { sex = it },
                            textStyle = TextStyle(fontSize = 18.sp)
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Gray)
                        )
                        Spacer(
                            modifier = Modifier
                                .width(120.dp)
                                .height(1.dp)
                                .background(Black)
                        )
                    }
                }

                Text(
                    text = "Выберите, что для Вас может быть интересно в наших ресторанах:",
                    fontStyle = FontStyle.Italic, color = Gray,
                    modifier = Modifier.padding(top = 20.dp, end = 4.dp, start = 20.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = check1, onCheckedChange = { check1 = it })
                    Text(text = "Караоке")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = check2, onCheckedChange = { check2 = it })
                    Text(text = "Мастер-классы")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = check3, onCheckedChange = { check3 = it })
                    Text(text = "Новинки меню")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
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
                    modifier = Modifier.padding(top = 10.dp)
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

                Button(
                    onClick = {
                        mVm.putDataToDocument(
                            FirePath("users", sharedPreferences.read()), hashMapOf(
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
                        ) {}
                        navController.navigate("UserInfo")
                    }, modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .height(LocalConfiguration.current.screenHeightDp.dp / 14),
                    colors = ButtonDefaults.buttonColors(backgroundColor = back), enabled = check5
                ) {
                    Text(text = "Сохранить", fontSize = 16.sp)
                }
            }
        }
    }
    BackHandler {
        navController.navigate("UserInfo")
    }
}