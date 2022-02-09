package com.lm.repository.ui.screens.onmainscreen

import androidx.activity.compose.BackHandler
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.lm.repository.core.SharedPrefProvider
import com.lm.repository.ui.cells.ColumnFMS
import com.lm.repository.ui.viewmodels.MainViewModel
import com.lm.repository.ui.viewmodels.RegViewModel

@OptIn(ExperimentalMaterialApi::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@Composable
fun BonusCard(
    mVm: MainViewModel,
    auth: FirebaseAuth,
    navController: NavHostController,
    rVm: RegViewModel,
    sharedPreferences: SharedPrefProvider
) {
    ColumnFMS {
        Text(text = "BonusCard")
    }

}