package com.lm.repository.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lm.repository.R
import com.lm.repository.core.Resource
import com.lm.repository.core.SharedPrefProvider
import com.lm.repository.data.models.FirePath
import com.lm.repository.ui.cells.ColumnFMS
import com.lm.repository.ui.viewmodels.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun UserInfo(mainViewModel: MainViewModel, sharedPreferences: SharedPrefProvider) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    val start by remember { mutableStateOf("") }


    LaunchedEffect(start){
        mainViewModel.dataFromDocumentV(FirePath("users",
            sharedPreferences.read())).collect {
            if (it is Resource.Success) {
                val t = it.data?.get("phone").toString()
                phone = "${t.substring(0, 2)} (${t.substring(2, 5)}) ${t.substring(5, 8)}-${t.substring(8, 10)}-${t.substring(10, 12)} "
                name = it.data?.get("name").toString()
            }
        }
    }

    ColumnFMS(vertArr = Arrangement.Top) {
        Image(
            painter = painterResource(id = R.drawable.b),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(
                    LocalConfiguration.current.screenHeightDp.dp / 3
                )
                .fillMaxWidth()
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 40.dp, end = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(text = name, fontSize = 20.sp)
                Text(text = phone, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 6.dp))
            }
            Image(Icons.Default.Edit, contentDescription = null, modifier = Modifier.clickable {

            })
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 30.dp, end = 30.dp)){
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Black))

            Row( Modifier
                .fillMaxWidth()
                .height(LocalConfiguration.current.screenHeightDp.dp / 14)
                .padding(start = 10.dp, end = 10.dp).clickable {

                },
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
              ) {
               Text(text = "Ваш профиль", color = Gray)
                Icon(Icons.Default.ChevronRight, contentDescription = null)
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Gray))

            Row( Modifier
                .fillMaxWidth()
                .height(LocalConfiguration.current.screenHeightDp.dp / 14)
                .padding(start = 10.dp, end = 10.dp).clickable {

                },
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Список заказов", color = Gray)
                Icon(Icons.Default.ChevronRight, contentDescription = null)
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Gray))

            Row( Modifier
                .fillMaxWidth()
                .height(LocalConfiguration.current.screenHeightDp.dp / 14)
                .padding(start = 10.dp, end = 10.dp).clickable {

                },
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Адреса", color = Gray)
                Icon(Icons.Default.ChevronRight, contentDescription = null)
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Gray))
        }
    }
}