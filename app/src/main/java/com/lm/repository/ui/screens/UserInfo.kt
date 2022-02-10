package com.lm.repository.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.lm.repository.MainActivity
import com.lm.repository.R
import com.lm.repository.core.Resource
import com.lm.repository.core.SharedPrefProvider
import com.lm.repository.data.models.FirePath
import com.lm.repository.theme.back
import com.lm.repository.theme.backScreen
import com.lm.repository.ui.cells.ColumnFMS
import com.lm.repository.ui.viewmodels.MainViewModel
import com.lm.repository.ui.viewmodels.RegViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun UserInfo(
    mainViewModel: MainViewModel,
    sharedPreferences: SharedPrefProvider,
    navController: NavHostController,
    rVm: RegViewModel
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    val start by remember { mutableStateOf("") }

    LaunchedEffect(start) {
        mainViewModel.dataFromDocumentV(
            FirePath(
                "users",
                sharedPreferences.read()
            )
        ).collect {
            if (it is Resource.Success) {
                it.data?.get("phone")?.toString()?.apply {
                    phone = "${substring(0, 2)} (${substring(2, 5)}) ${substring(5, 8)}-${
                        substring(8, 10)
                    }-${substring(10, 12)} "
                }

                it.data?.apply {
                    val sN = get("sName")?.toString() ?: ""
                    val p = get("patr")?.toString() ?: ""
                    val n = get("name")?.toString() ?: ""
                    name = "$n $p $sN"
                    if (p.isEmpty()) name = "$n $sN"
                    if (n.isEmpty()) name = "$p $sN"
                    if (n.isEmpty() && p.isEmpty() && sN.isEmpty()) name = "NoName"
                }
            }
        }
    }

    ColumnFMS(vertArr = Arrangement.Top) {
        Image(
            painter = painterResource(id = R.drawable.b),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(LocalConfiguration.current.screenHeightDp.dp / 3)
                .fillMaxWidth()
        )

            Column(Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 40.dp, end = 20.dp),
                horizontalAlignment = Alignment.Start) {
                Text(text = name, fontSize = 20.sp)
                Text(
                    text = phone,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 30.dp, end = 30.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Black)
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenHeightDp.dp / 14)
                    .padding(start = 10.dp, end = 10.dp)
                    .clickable {
                        navController.navigate("MyProfile")
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Редактировать профиль", color = Gray)
                Icon(Icons.Default.ChevronRight, contentDescription = null)
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Gray)
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenHeightDp.dp / 14)
                    .padding(start = 10.dp, end = 10.dp)
                    .clickable {
                        navController.navigate("OrdersList")
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Список заказов", color = Gray)
                Icon(Icons.Default.ChevronRight, contentDescription = null)
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Gray)
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenHeightDp.dp / 14)
                    .padding(start = 10.dp, end = 10.dp)
                    .clickable {
                        navController.navigate("Addresses")
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Адреса", color = Gray)
                Icon(Icons.Default.ChevronRight, contentDescription = null)
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Gray)
            )
            val mainActivity = LocalContext.current as MainActivity
            Button(
                onClick = {
                    rVm.signOut()
                    Toast.makeText(mainActivity, "Вы вышли из аккаунта", Toast.LENGTH_LONG)
                        .show()
                    navController.navigate("MainScreen")
                          }, modifier = Modifier
                    .padding(top = 30.dp, start = 10.dp, end = 10.dp)
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenHeightDp.dp / 18),
                colors = ButtonDefaults.buttonColors(backgroundColor = back)
            ) {
                Text(text = "Выйти", fontSize = 16.sp, color = Red)
            }
        }
    }
    BackHandler {
        navController.navigate(backScreen)
    }
}