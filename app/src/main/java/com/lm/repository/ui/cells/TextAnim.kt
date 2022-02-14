package com.lm.repository.ui.cells

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.lm.repository.di.MainDep.depends
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@Composable
fun TextAnim(text: String) {
    var rotation by remember { mutableStateOf(1f) }
    val coroutine = rememberCoroutineScope()
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = depends.screenHeight/22),
        horizontalArrangement = Arrangement.Center
    ) {
        text.forEachIndexed { index, c ->
            Text(text = c.toString(), modifier = Modifier
                .graphicsLayer { rotationX = rotation + index * 350 },
                textAlign = TextAlign.Center, fontSize = 18.sp)
        }
        LaunchedEffect(true) {
            coroutine.launch {
                (0 until 1000).asFlow().onEach {
                    delay(1)
                }.collect {
                    rotation += 1f
                    if (rotation == 350f) rotation = 1f
                }
            }
        }
    }

    DisposableEffect(true) {
        onDispose { coroutine.cancel()
        }
    }
}


