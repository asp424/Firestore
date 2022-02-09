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
import com.lm.repository.ui.cells.BottomSheet
import com.lm.repository.ui.viewmodels.MainViewModel
import com.lm.repository.ui.viewmodels.RegViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@Composable
fun Delivery(
    mVm: MainViewModel,
    auth: FirebaseAuth,
    navController: NavHostController,
    rVm: RegViewModel,
    sharedPreferences: SharedPrefProvider
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutine = rememberCoroutineScope()

    ModalDrawer(drawerContent = {

    }, drawerState = drawerState, content = {
        Row(
            modifier = Modifier
                .padding(top = 18.dp, start = 12.dp, end = 12.dp, bottom = 10.dp)
                .fillMaxWidth()
                .height(65.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Icon(
                Icons.Default.Menu,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        coroutine.launch {
                            drawerState.animateTo(DrawerValue.Open, tween(500))
                        }
                    },
                tint = Color.DarkGray
            )
            Row {
                Image(
                    painterResource(id = R.drawable.onion),
                    contentDescription = null, modifier = Modifier
                        .size(30.dp)
                        .padding(end = 6.dp)
                )
                Text(
                    text = "Доставка и самовывоз",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }

            Icon(
                Icons.Default.AccountBox,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        if (auth.currentUser == null)
                            coroutine.launch {
                                if (bottomSheetState.isVisible) {
                                    bottomSheetState.animateTo(ModalBottomSheetValue.Hidden)
                                } else {
                                    bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }
                            }
                        else navController.navigate("UserInfo")
                    }
            )
        }
    })

    BottomSheet(auth, mVm, rVm, sharedPreferences, navController, bottomSheetState, "reg")

    BackHandler {
        navController.navigate("MainScreen")
    }
}