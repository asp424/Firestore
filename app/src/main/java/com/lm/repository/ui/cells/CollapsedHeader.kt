package com.lm.repository.ui.cells

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.lm.repository.di.MainDep.depends
import com.lm.repository.ui.screens.list
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CollapsedHeader(header: String, content: @Composable (ColumnScope) -> Unit) {

    depends.apply {
        val itemHeight = screenHeight / 8
        val state = rememberScrollState()
        var height by remember { mutableStateOf(itemHeight) }
        if (height <= itemHeight && state.value != state.maxValue)
            LaunchedEffect(state.value) {
                if (itemHeight - state.value.dp / 3 >= 0.dp)
                height = itemHeight - state.value.dp / 3
            }

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(state).background(Color.Gray), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.size(screenWidth, height).padding(top = 30.dp)
            ) {
                Text(
                    text = header,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier


                )
                Text(text = "XXXXXXXXXX")
            }
            content(this)
        }
    }
}