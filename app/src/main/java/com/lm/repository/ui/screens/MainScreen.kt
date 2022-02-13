package com.lm.repository.ui.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lm.repository.MainActivity
import com.lm.repository.R
import com.lm.repository.di.MainDep.depends
import com.lm.repository.di.backScreen
import com.lm.repository.theme.back
import com.lm.repository.ui.cells.CollapsedList
import com.lm.repository.ui.utils.backAction
import com.lm.repository.ui.utils.expandBottomSheet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


val list = listOf(
    R.drawable.a,
    R.drawable.b,
    R.drawable.c,
    R.drawable.e,
    R.drawable.d
)
private val listButtons = listOf(
    "ДОСТАВКА И САМОВЫВОЗ",
    "РЕСТОРАНЫ",
    "МЕНЮ РЕСТОРАНА",
    "ЗАБРОНИРОВАТЬ СТОЛИК",
    "БОНУСНАЯ КАРТА"
)

private val listButtonsNav = listOf(
    "Delivery",
    "Restaurants",
    "Menu",
    "Booking",
    "BonusCard"
)

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(
    ExperimentalCoroutinesApi::class, com.google.accompanist.pager.ExperimentalPagerApi::class,
    ExperimentalMaterialApi::class, androidx.compose.animation.ExperimentalAnimationApi::class
)
@Composable
fun MainScreen() {
    depends.apply {
        CollapsedList {
            Text(
                text = "XXXXXXXXXXXXXXXXX",
                modifier = Modifier.padding(top = 20.dp)
            )

            Column(modifier = Modifier.padding(top = 20.dp)) {
                listButtons.forEachIndexed { i, it ->
                    depends.fireAuth.apply {
                        depends.mainViewModel.also { mVm ->
                            Button(
                                onClick = {
                                    coroutine.launch {
                                        when (listButtonsNav[i]) {
                                            "BonusCard" -> expandBottomSheet(
                                                mVm,
                                                if (this@apply.currentUser != null) "bonusCard"
                                                else "reg"
                                            )
                                            "Booking" -> expandBottomSheet(
                                                mVm,
                                                "booking"
                                            )
                                            else -> {
                                                mainViewModel.setDrawerHeader(
                                                    when (listButtonsNav[i]) {
                                                        "Menu" -> "Меню ресторана"
                                                        "Restaurants" -> "Рестораны"
                                                        "Delivery" -> "Доставка и самовывоз"
                                                        else -> ""
                                                    }
                                                )
                                                navController.navigate(listButtonsNav[i])
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .width(screenWidth)
                                    .padding(start = 40.dp, end = 40.dp)
                                    .height(screenHeight / 16),
                                colors = ButtonDefaults.buttonColors(backgroundColor = back),
                                shape = RoundedCornerShape(8.dp),
                                elevation = ButtonDefaults.elevation(
                                    defaultElevation = 6.dp,
                                    pressedElevation = 60.dp,
                                    hoveredElevation = 20.dp,
                                    focusedElevation = 40.dp
                                ),
                                border = BorderStroke(1.dp, Gray)
                            ) {
                                Text(text = it, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "СОБЫТИЯ",
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = 15.dp, top = 15.dp),
                        fontWeight = FontWeight.ExtraLight
                    )
                }

                LazyRow(
                    content = {
                        items(5) { ind ->
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                elevation = 20.dp,
                                modifier =
                                Modifier
                                    .width(screenWidth / 2)
                                    .height(280.dp)
                                    .padding(
                                        top = 15.dp,
                                        bottom = 15.dp,
                                        end = if (ind == 4) 0.dp else 15.dp
                                    )

                            ) {
                                Column {
                                    Image(
                                        painter = painterResource(id = list[ind]),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .height(180.dp)
                                            .align(CenterHorizontally)
                                    )
                                    Text(
                                        text = "aaaaaaaaaaaaaaaaaaaaaaaaaa",
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }
                            }
                        }
                    },
                    contentPadding = PaddingValues(start = 30.dp, end = 30.dp),
                )
                Image(
                    painter = painterResource(id = R.drawable.ass),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .width(screenWidth)
                        .height(70.dp)
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(Red)
                        .height(40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Andrey Valerich Ⓒ 2022",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = White,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            (LocalContext.current as MainActivity).apply {
                BackHandler {
                    coroutine.launch {
                        backAction(
                            bottomSheetState,
                            drawerState
                        ) { finish() }
                    }
                }
            }
            backScreen = "MainScreen"
        }
    }
}





