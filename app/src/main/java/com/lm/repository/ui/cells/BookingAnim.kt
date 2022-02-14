package com.lm.repository.ui.cells

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.lm.repository.di.MainDep.depends

@Composable
fun BookingAnim() {

    depends.apply {
        var start1 by remember { mutableStateOf(false) }
        val dp1 by animateDpAsState(targetValue =
        if (!start1) screenWidth/4 else screenWidth
        )
        var start by remember { mutableStateOf(false) }
        ColumnFMS {
            repeat(3) {
                Row(Modifier.padding(bottom = 10.dp)) {
                    repeat(3) {
                        var start by remember { mutableStateOf(false) }
                        val dp by animateDpAsState(targetValue =
                        if (!start) screenWidth/4 else screenWidth
                        )
                        Card(
                            Modifier
                                .size(dp1)

                                .clickable {
start1 = !start1
                                }
                                , border = BorderStroke(1.dp, Color.Black)) {

                        }
                    }
                }
            }
        }
    }
}