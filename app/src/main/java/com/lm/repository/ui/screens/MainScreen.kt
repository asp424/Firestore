package com.lm.repository.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.lm.repository.R
import com.lm.repository.core.SharedPrefProvider
import com.lm.repository.theme.back
import com.lm.repository.ui.cells.ColumnFMS
import com.lm.repository.ui.viewmodels.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


val list = listOf(R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.e, R.drawable.d)
private val listButtons = listOf(
    "ДОСТАВКА И САМОВЫВОЗ",
    "РЕСТОРАНЫ",
    "МЕНЮ РЕСТОРАНА",
    "ЗАБРОНИРОВАТЬ СТОЛИК",
    "БОНУСНАЯ КАРТА"
)

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalCoroutinesApi::class, com.google.accompanist.pager.ExperimentalPagerApi::class)
@Composable
fun MainScreen(mainViewModel: MainViewModel, sharedPreferences: SharedPrefProvider) {
    val pagerState = rememberPagerState()
    val pagerState1 = rememberPagerState()
    val coroutine = rememberCoroutineScope()
    var text by remember { mutableStateOf("") }
    val pageState by mainViewModel.pagerState.collectAsState()

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

    Row(
        modifier = Modifier
            .padding(top = 18.dp, start = 12.dp, end = 12.dp, bottom = 10.dp)
            .fillMaxWidth()
            .height(65.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Icon(
            Icons.Default.Menu,
            contentDescription = null,
            modifier = Modifier.size(30.dp),
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
            modifier = Modifier.size(30.dp)
        )
    }

    ColumnFMS(vertArr = Arrangement.Top, modifier = Modifier.padding(top = 60.dp)) {
        Box {
            HorizontalPager(count = list.size, state = pagerState) { page ->
                Image(
                    painter = painterResource(id = list[page]),
                    contentDescription = null,
                    Modifier.size(
                        LocalConfiguration.current.screenWidthDp.dp, 200.dp
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
                        .height(42.dp),
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
                            .width(210.dp)
                            .height(280.dp)
                            .padding(start = 15.dp, top = 15.dp, bottom = 15.dp, end = 15.dp)

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
            Modifier.fillMaxWidth().background(
                Red
            ).height(40.dp)){
            Text(text = "Andrey Valerich Ⓒ 2022",
                textAlign = TextAlign.Center, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = White)
        }
    }
}
