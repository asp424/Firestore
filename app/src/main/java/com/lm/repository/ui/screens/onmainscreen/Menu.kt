package com.lm.repository.ui.screens.onmainscreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DrawerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.lm.repository.di.MainDep
import com.lm.repository.di.backScreen
import com.lm.repository.ui.cells.ColumnFMS
import com.lm.repository.ui.utils.backAction
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@Composable
fun Menu() {
    MainDep.depends.apply {

        ColumnFMS(vertArr = Arrangement.Top, modifier = Modifier.padding(top = 59.dp)) {
            Text(text = "Menu")
        }

        BackHandler {
            coroutine.launch {
                backAction(bottomSheetState, drawerState) { navController.navigate("MainScreen") }
            }
        }
        backScreen = "Menu"
    }
}