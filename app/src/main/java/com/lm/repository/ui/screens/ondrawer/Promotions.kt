package com.lm.repository.ui.screens.ondrawer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lm.repository.di.MainDep
import com.lm.repository.di.backScreen
import com.lm.repository.ui.cells.ColumnFMS
import com.lm.repository.ui.utils.backAction
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Promotions() {
    MainDep.depends.apply {
        val coroutine = rememberCoroutineScope()
        ColumnFMS(vertArr = Arrangement.Top, modifier = Modifier.padding(top = 59.dp)) {
            Text(text = "Promotions")
        }
        BackHandler {
            coroutine.launch {
                backAction(bottomSheetState, drawerState) {
                    mainViewModel.setDrawerHeader("Акции")
                    navController.navigate("MainScreen") }
            }
        }
        backScreen = "Promotions"
    }
}