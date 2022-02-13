package com.lm.repository.ui.screens.onuserinfo

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.lm.repository.core.SharedPrefProvider
import com.lm.repository.di.MainDep
import com.lm.repository.di.MainDep.depends
import com.lm.repository.ui.cells.CollapsedHeader
import com.lm.repository.ui.cells.ColumnFMS
import com.lm.repository.ui.viewmodels.MainViewModel
import com.lm.repository.ui.viewmodels.RegViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun OrdersList() {
    depends.apply {
        CollapsedHeader(header = "ВАШИ ЗАКАЗЫ") {}

        BackHandler {
            navController.navigate("UserInfo")
        }
    }
}