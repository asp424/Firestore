package com.lm.repository.ui.screens.onmainscreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lm.repository.di.MainDep.depends
import com.lm.repository.di.backScreen
import com.lm.repository.ui.cells.ColumnFMS
import com.lm.repository.ui.utils.backAction
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@Composable
fun Delivery() {
    depends.apply {
        val coroutine = rememberCoroutineScope()
        ColumnFMS(vertArr = Arrangement.Top, modifier = Modifier.padding(top = 59.dp)) {
            Text(text = "Delivery")
        }
        BackHandler {
            coroutine.launch {
                backAction(bottomSheetState, drawerState) {
                    mainViewModel.setDrawerHeader("Главная")
                    navController.navigate("MainScreen") }
            }
        }
        backScreen = "Delivery"
    }
}