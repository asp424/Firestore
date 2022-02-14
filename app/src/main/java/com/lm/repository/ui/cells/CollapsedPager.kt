package com.lm.repository.ui.cells

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.lm.repository.di.MainDep.depends
import com.lm.repository.ui.screens.list
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CollapsedPager(content: @Composable (ColumnScope) -> Unit) {

    depends.apply {
        val t by remember { mutableStateOf("") }
        val itemHeight = screenHeight / 3
        val state = rememberScrollState()
        val pagerState = rememberPagerState()
        var height by remember { mutableStateOf(itemHeight) }
        LaunchedEffect(t) {
            (0 until 1000).asFlow().onEach { delay(3_000) }.collect {
                pagerState.apply {
                    if (currentPage == list.lastIndex) animateScrollToPage(0)
                    else animateScrollToPage(currentPage + 1)
                }
            }
        }
        if (height <= itemHeight && state.value != state.maxValue && itemHeight - state.value.dp / 3 >= 0.dp)
            LaunchedEffect(state.value) {
                height = itemHeight - state.value.dp / 3
            }

        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 59.dp)
                .verticalScroll(state), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box {
                HorizontalPager(count = list.size, state = pagerState) { page ->
                    Image(
                        painter = painterResource(id = list[page]),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier =
                        Modifier.size(depends.screenWidth, height - depends.screenWidth / 12)
                    )
                }

                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomCenter),
                )
            }
            content(this)
        }
    }
}