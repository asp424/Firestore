package com.lm.repository.ui.cells

import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Top
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lm.repository.R
import com.lm.repository.di.MainDep.depends
import com.lm.repository.theme.Purple700
import com.lm.repository.theme.back
import com.lm.repository.ui.navigator.Screens
import com.lm.repository.ui.navigator.screen
import com.lm.repository.ui.utils.expandBottomSheet
import kotlinx.coroutines.launch

private val listButtonsNav = listOf(
    "Delivery",
    "Restaurants",
    "Menu",
    "Events",
    "Promotions",
    "Feedback",
    "About",
    "Reference"
)

private val listButtons = listOf(
    "ДОСТАВКА И САМОВЫВОЗ",
    "РЕСТОРАНЫ",
    "МЕНЮ РЕСТОРАНА",
    "СОБЫТИЯ",
    "АКЦИИ",
    "ОСТАВИТЬ ОТЗЫВ",
    "О НАС",
    "СПРАВКА"
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DrawerContent() {
    depends.apply {
        ColumnFMS(vertArr = Top, horArr = Start, modifier = Modifier.background(Purple700)) {
            Image(
                painter = painterResource(id = R.drawable.c),
                contentDescription = null,
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .height(screenHeight / 6), contentScale = ContentScale.Crop
            )
            listButtons.forEachIndexed { i, item ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            when (i) {
                                0 -> navController.navigate(screen(Screens.Delivery))
                                1 -> navController.navigate(screen(Screens.Restaurants))
                                2 -> navController.navigate(screen(Screens.Menu))
                                3 -> navController.navigate(screen(Screens.Events))
                                4 -> navController.navigate(screen(Screens.Promotions))
                                5 -> expandBottomSheet(
                                    mainViewModel,
                                    "feedback"
                                )
                                6 -> navController.navigate(screen(Screens.About))
                                7 -> navController.navigate(screen(Screens.Reference))
                            }
                            coroutine.launch {
                                drawerState.animateTo(
                                    DrawerValue.Closed,
                                    tween(700)
                                )
                            }
                        }) {
                    Text(
                        text = item,
                        modifier = Modifier.padding(bottom = 16.dp, start = 20.dp),
                        color = Color.White, fontWeight = FontWeight.Bold
                    )
                }

            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
                    .padding(top = 6.dp)
            )
            Row(
                modifier = Modifier.padding(start = 20.dp, top = 14.dp, bottom = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Outlined.CreditCard,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 10.dp),
                    tint = Color.White
                )
                depends.fireAuth.apply {
                    Row(Modifier.fillMaxWidth().clickable {
                        expandBottomSheet(
                            mainViewModel,
                            if (this@apply?.currentUser != null) "bonusCard"
                            else "reg"
                        )
                        coroutine.launch { drawerState.animateTo(DrawerValue.Closed, tween(700)) }
                    }) {
                        Text(
                            text = "БОНУСНАЯ КАРТА",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
            Box(contentAlignment = Center, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { }, modifier = Modifier
                        .padding(top = 10.dp)
                        .width(screenWidth - screenWidth / 3)
                        .height(screenHeight / 16),
                    colors = ButtonDefaults.buttonColors(backgroundColor = back),
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 60.dp,
                        hoveredElevation = 20.dp,
                        focusedElevation = 40.dp
                    ),
                    border = BorderStroke(1.dp, Color.Gray)
                ) {
                    Text(text = "ЗАБРОНИРОВАТЬ СТОЛИК")
                }

            }
            Image(
                painter = painterResource(id = R.drawable.d),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.padding(top = 40.dp)
            )
        }
    }
}