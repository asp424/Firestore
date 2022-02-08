package com.lm.repository.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.lm.repository.MainActivity
import com.lm.repository.R
import com.lm.repository.core.SharedPrefProvider
import com.lm.repository.data.repository.registration.RegResponse
import com.lm.repository.di.registration.components.DaggerRegComponent
import com.lm.repository.theme.back
import com.lm.repository.ui.cells.ColumnFMS
import com.lm.repository.ui.viewmodels.MainViewModel
import com.lm.repository.ui.viewmodels.RegViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


val list = listOf(R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.e, R.drawable.d)
private val listButtons = listOf(
    "ДОСТАВКА И САМОВЫВОЗ",
    "РЕСТОРАНЫ",
    "МЕНЮ РЕСТОРАНА",
    "ЗАБРОНИРОВАТЬ СТОЛИК",
    "БОНУСНАЯ КАРТА"
)

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(
    ExperimentalCoroutinesApi::class, com.google.accompanist.pager.ExperimentalPagerApi::class,
    ExperimentalMaterialApi::class, androidx.compose.animation.ExperimentalAnimationApi::class
)
@Composable
fun MainScreen(mainViewModel: MainViewModel, sharedPreferences: SharedPrefProvider) {
    val pagerState = rememberPagerState()
    val pageState by mainViewModel.pagerState.collectAsState()
    var visible by remember { mutableStateOf(true) }
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val mainActivity = LocalContext.current as MainActivity
    val coroutine = rememberCoroutineScope()
    LocalLifecycleOwner.current.apply {
        LaunchedEffect(this) {
            lifecycle.addObserver(mainViewModel)
        }
    }

    LaunchedEffect(pageState) {
        pagerState.apply {
            if (currentPage == list.lastIndex) animateScrollToPage(0)
            else animateScrollToPage(currentPage + 1)
        }
    }

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
                            drawerState.open()
                        }
                    },
                tint = DarkGray
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

            Icon(
                Icons.Default.AccountBox,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        coroutine.launch {
                            if (bottomSheetState.isVisible)
                                bottomSheetState.animateTo(ModalBottomSheetValue.Hidden)
                            else bottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                        }
                    }
            )
        }
        ColumnFMS(vertArr = Arrangement.Top, modifier = Modifier.padding(top = 60.dp)) {
            Box {
                HorizontalPager(count = list.size, state = pagerState) { page ->
                    Image(
                        painter = painterResource(id = list[page]),
                        contentDescription = null,
                        Modifier.size(
                            LocalConfiguration.current.screenWidthDp.dp,
                            LocalConfiguration.current.screenHeightDp.dp / 3
                        )
                    )
                }

                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomCenter),
                )
            }

            Text(text = "XXXXXXXXXXXXXXXXX", modifier = Modifier.padding(top = 20.dp))

            Column(modifier = Modifier.padding(top = 20.dp)) {
                listButtons.forEach {
                    Button(
                        onClick = { }, modifier = Modifier
                            .padding(bottom = 10.dp)
                            .width(250.dp)
                            .height(LocalConfiguration.current.screenHeightDp.dp / 19),
                        colors = ButtonDefaults.buttonColors(backgroundColor = back)
                    ) {
                        Text(text = it, fontWeight = FontWeight.Bold)
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
                            shape = RoundedCornerShape(10.dp), elevation = 20.dp, modifier =
                            Modifier
                                .width(LocalConfiguration.current.screenWidthDp.dp / 2)
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
                                    contentDescription = null, modifier = Modifier
                                        .height(100.dp)
                                        .align(CenterHorizontally)
                                )
                                Text(
                                    text = "aaaaaaaaaaaaaaaaaaaaaaaaaa",
                                    textAlign = TextAlign.Start, modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }
                },
                contentPadding = PaddingValues(start = 30.dp, end = 30.dp),
            )
            Image(
                painter = painterResource(id = R.drawable.ass),
                contentDescription = null, modifier = Modifier
                    .padding(top = 20.dp)
                    .width(
                        LocalConfiguration.current.screenWidthDp.dp
                    )
                    .height(70.dp)
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Red)
                    .height(40.dp), verticalAlignment = Alignment.CenterVertically
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

        ModalBottomSheetLayout(
            sheetContent = {
                Card(
                    shape = RoundedCornerShape(20.dp), border = BorderStroke(2.dp, Black),
                    modifier = Modifier.fillMaxSize()
                ) {
                    CheckForAuth(mainActivity = mainActivity) {
                        coroutine.launch {
                            bottomSheetState.hide()
                            Toast.makeText(mainActivity, "Вы вошли", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }, sheetState = bottomSheetState, sheetShape = RoundedCornerShape(20.dp),
            modifier = Modifier.padding(top = 80.dp), sheetBackgroundColor = Gray
        ) {
        }
    })
}

@Composable
@OptIn(ExperimentalCoroutinesApi::class)
private fun CheckForAuth(
    mainActivity: MainActivity, regCallback: (RegResponse) -> Unit
) {
    FirebaseAuth.getInstance().apply {
        if (currentUser != null) {

        } else
            DaggerRegComponent.builder()
                .dispatcher(Dispatchers.IO)
                .authOptions(
                    PhoneAuthOptions.newBuilder(this).setActivity(mainActivity)
                )
                .authInstance(this)
                .sharedPreferences(
                    mainActivity.getSharedPreferences("id", ComponentActivity.MODE_PRIVATE)
                )
                .create().apply {
                    sharedPreferences().apply {
                        viewModelFactory().also {
                            val regViewModel by mainActivity.viewModels<RegViewModel> { it }
                            RegScreen(regViewModel, this) { regCallback(it) }
                        }
                    }
                }
    }
}








