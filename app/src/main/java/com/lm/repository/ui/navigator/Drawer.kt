package com.lm.repository.ui.navigator

import androidx.compose.material.ModalDrawer
import androidx.compose.runtime.Composable
import com.lm.repository.di.MainDep.depends

@Composable
fun Drawer(content: @Composable () -> Unit) {

    ModalDrawer(drawerContent = {

    }, drawerState = depends.drawerState, content = {
        content()
    })
}