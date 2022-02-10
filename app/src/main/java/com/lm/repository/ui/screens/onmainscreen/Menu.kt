package com.lm.repository.ui.screens.onmainscreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.lm.repository.R
import com.lm.repository.core.SharedPrefProvider
import com.lm.repository.ui.cells.ColumnFMS
import com.lm.repository.ui.navigator.BottomSheet
import com.lm.repository.ui.viewmodels.MainViewModel
import com.lm.repository.ui.viewmodels.RegViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@Composable
fun Menu(
    mVm: MainViewModel,
    auth: FirebaseAuth,
    navController: NavHostController,
    rVm: RegViewModel,
    sharedPreferences: SharedPrefProvider,
    bottomSheetState: ModalBottomSheetState,
    drawerState: DrawerState
) {
    val coroutine = rememberCoroutineScope()

    ColumnFMS(vertArr = Arrangement.Top, modifier = Modifier.padding(top = 59.dp)) {
        Text(text = "Menu")
    }

    BackHandler {
        if (bottomSheetState.isVisible)
            coroutine.launch { bottomSheetState.animateTo(ModalBottomSheetValue.Hidden,
                tween(700)) }
        if (drawerState.isOpen) coroutine.launch {
            drawerState.animateTo(DrawerValue.Closed, tween(700))
        }
        if (!bottomSheetState.isVisible && !drawerState.isOpen)  navController.navigate("MainScreen")
    }
}