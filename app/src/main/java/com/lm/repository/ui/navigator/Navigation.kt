package com.lm.repository.ui.navigator

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lm.repository.R
import com.lm.repository.di.MainDep.depends
import com.lm.repository.ui.cells.NavHost
import com.lm.repository.ui.utils.expandBottomSheet
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterialApi::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class,
    androidx.compose.animation.ExperimentalAnimationApi::class
)
@Composable
fun Navigation() {
    depends.apply {
        ModalDrawer(drawerContent = {

        }, drawerState = drawerState, content = {
            Row(
                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp)
                    .fillMaxWidth()
                    .height(59.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
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
                        text = "Главная",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }
                depends.fireAuth.apply {
                    depends.mainViewModel.also {
                        Icon(
                            Icons.Default.AccountBox,
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    if (this.currentUser == null) coroutine.launch {
                                        expandBottomSheet(it, "reg")
                                    }
                                    else navController.navigate("UserInfo")
                                }
                        )
                    }
                }
            }
            NavHost()
            BottomSheet()
        })
    }
}

