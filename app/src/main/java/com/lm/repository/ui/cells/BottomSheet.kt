package com.lm.repository.ui.cells

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.lm.repository.MainActivity
import com.lm.repository.core.SharedPrefProvider
import com.lm.repository.ui.screens.RegScreen
import com.lm.repository.ui.screens.onmainscreen.BonusCard
import com.lm.repository.ui.screens.onmainscreen.Booking
import com.lm.repository.ui.viewmodels.MainViewModel
import com.lm.repository.ui.viewmodels.RegViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@Composable
fun BottomSheet(
    auth: FirebaseAuth,
    mVm: MainViewModel,
    rVm: RegViewModel,
    sharedPreferences: SharedPrefProvider,
    navController: NavHostController,
    bottomSheetState: ModalBottomSheetState,
    screen: String
) {
    val mainActivity = LocalContext.current as MainActivity
    val coroutine = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetContent = {
            Card(
                shape = RoundedCornerShape(20.dp), border = BorderStroke(4.dp, Color.Black),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 1.dp, end = 1.dp, bottom = 16.dp)
            ) {
                when (screen) {
                    "reg" -> if (auth.currentUser?.uid == null) {
                        RegScreen(
                            viewModel = rVm,
                            mainViewModel = mVm,
                            sharedPrefProvider = sharedPreferences,
                            regCallback = { res, name ->
                                coroutine.launch {
                                    bottomSheetState.hide()
                                    if (name.isNotEmpty())
                                        Toast.makeText(
                                            mainActivity,
                                            "С возвращением, $name!",
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                    else Toast.makeText(
                                        mainActivity,
                                        "Вы зарегестрированы",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        )
                    }
                    "bonusCard" -> BonusCard(
                        mVm = mVm,
                        auth = auth,
                        navController = navController,
                        rVm = rVm,
                        sharedPreferences = sharedPreferences
                    )
                    "booking" -> Booking(
                        mVm = mVm,
                        auth = auth,
                        navController = navController,
                        rVm = rVm,
                        sharedPreferences = sharedPreferences
                    )
                }
            }
        }, sheetState = bottomSheetState, sheetShape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(top = 80.dp), sheetBackgroundColor = Color.Gray
    ) {
    }
    BackHandler {
        if (bottomSheetState.isVisible)
            coroutine.launch {
                bottomSheetState.hide()
            }
    }
}