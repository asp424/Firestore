package com.lm.repository.ui.navigator

import android.graphics.Color.GRAY
import android.graphics.Color.alpha
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.lm.repository.MainActivity
import com.lm.repository.di.MainDep.depends
import com.lm.repository.di.bottomSheetContent
import com.lm.repository.ui.cells.BookingAnim
import com.lm.repository.ui.screens.ondrawer.Feedback
import com.lm.repository.ui.screens.onmainscreen.BonusCard
import com.lm.repository.ui.screens.onmainscreen.Booking
import com.lm.repository.ui.screens.registration.RegScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@Composable
fun BottomSheet() {
    val controller by depends.mainViewModel.bottomSheet.collectAsState()
val dens = LocalDensity.current.density
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
                        border = BorderStroke(2.dp, Color.Black)
                    ) {
                        Canvas(modifier = Modifier.alpha(0.6f), onDraw = {
                            drawRoundRect(topLeft = Offset(screenWidth.value/2 * dens - 100f, 50f),
                                color = Color.Gray, size = Size(200f, 20f),
                                cornerRadius = CornerRadius(20f))
                        })
                        when (bottomSheetContent) {
                            "reg" ->
                                RegScreen(
                                    regCallback = { res, name ->
                                        coroutine.launch {
                                            bottomSheetState.hide()
                                            Toast.makeText(act,
                                                if (name.isNotEmpty()) "?? ????????????????????????, $name!"
                                                else "???? ????????????????????????????????", Toast.LENGTH_LONG
                                            ).show()
                                            mainViewModel.apply {
                                                invisibleButton()
                                                readUser()
                                            }
                                        }
                                    }
                                )
                            "bonusCard" -> BonusCard()
                            "booking" -> BookingAnim()
                            "feedback" -> Feedback()
                            "" -> Text("ass", modifier = Modifier.size(1.dp))
                        }
                    }
                }, sheetState = bottomSheetState,
                sheetBackgroundColor = ModalBottomSheetDefaults.scrimColor,
                sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                content = {}, modifier = Modifier.alpha(0.97f)
            )
        }
    }
}