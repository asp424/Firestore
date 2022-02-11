package com.lm.repository.ui.navigator

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.lm.repository.MainActivity
import com.lm.repository.di.MainDep.depends
import com.lm.repository.di.bottomSheetContent
import com.lm.repository.ui.screens.onmainscreen.BonusCard
import com.lm.repository.ui.screens.onmainscreen.Booking
import com.lm.repository.ui.screens.registration.RegScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@Composable
fun BottomSheet() {
    val controller by depends.mainViewModel.bottomSheet.collectAsState()

    depends.apply {
        LaunchedEffect(controller) {
            if (controller != 1)
                bottomSheetState.apply {
                    if (isVisible && !isAnimationRunning) animateTo(
                        ModalBottomSheetValue.Hidden, tween(600)
                    )
                    else animateTo(
                        ModalBottomSheetValue.Expanded, tween(600)
                    )
                }
        }
        (LocalContext.current as MainActivity).also { act ->
            ModalBottomSheetLayout(
                sheetContent = {
                    Card(
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                        border = BorderStroke(2.dp, Color.Black),
                    ) {
                        when (bottomSheetContent) {
                            "reg" ->
                                RegScreen(
                                    regCallback = { res, name ->
                                        coroutine.launch {
                                            bottomSheetState.hide()
                                            Toast.makeText(act,
                                                if (name.isNotEmpty()) "С возвращением, $name!"
                                                else "Вы зарегестрированы", Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                )
                            "bonusCard" -> BonusCard()
                            "booking" -> Booking()
                            "" -> Text("ass", modifier = Modifier.size(1.dp))
                        }
                    }
                }, sheetState = bottomSheetState,
                sheetBackgroundColor = ModalBottomSheetDefaults.scrimColor,
                sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                content = {}
            )
        }
    }
}