package com.lm.repository.ui.navigator

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lm.repository.core.InternetListener
import com.lm.repository.di.MainDep.depends
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach

@OptIn(
    ExperimentalMaterialApi::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class,
    androidx.compose.animation.ExperimentalAnimationApi::class
)
@Composable
fun Navigation() {
    depends.apply {
        val internet by mainViewModel.internet.collectAsState()

        var text by remember {
            mutableStateOf("Отсутствует интернет")
        }

        var color by remember {
            mutableStateOf(Black)
        }
        var offsetY by remember { mutableStateOf(0f) }

        LocalLifecycleOwner.current.lifecycle.addObserver(depends.mainViewModel)

        InternetListener(callback = {
            mainViewModel.internet(it)
        })

        LaunchedEffect(internet) {
            Log.d("My", internet.toString())
            if (internet) {
                color = Black
                text = "Отсутствует интернет"
                (0 until 100).asFlow().onEach {
                    delay(10)
                }.collect {
                    if (offsetY != -100f)
                        offsetY -= 1f
                }
            }
            else {
                if (offsetY == -100f) {
                    color = Green
                    text = "Вы снова в сети"
                    delay(2000L)
                    (0 until 1000).asFlow().onEach {
                        delay(10)
                    }.collect {
                        if (offsetY != 0f)
                            offsetY += 1f
                    }
                }
            }
        }
        Drawer {
            DrawerTopBar()
            AnimatedNavHost()
            BottomSheet()
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(0.dp, screenHeight + 1.dp)
                    .height(screenHeight / 20)
                    .graphicsLayer {
                        translationY = offsetY
                    }, backgroundColor = color
            ) {
                Text(
                    text = text,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = screenHeight / 100)
                )
            }
        }
    }
}

