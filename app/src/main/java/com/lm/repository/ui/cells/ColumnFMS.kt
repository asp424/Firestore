package com.lm.repository.ui.cells

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ColumnFMS(
    paddingBottom: Dp = 0.dp,
    vertArr: Arrangement.Vertical = Arrangement.Center,
    modifier: Modifier = Modifier,
    inColumn: @Composable (ColumnScope) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(bottom = paddingBottom)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = vertArr
    ) {
        inColumn(this)
    }
}