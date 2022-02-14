package com.lm.repository.ui.navigator

import androidx.compose.material.ModalDrawer
import androidx.compose.runtime.Composable
import com.lm.repository.di.MainDep.depends
import com.lm.repository.ui.cells.DrawerContent

@Composable
fun Drawer(content: @Composable () -> Unit) {

    ModalDrawer(drawerContent = {
        DrawerContent()
    }, drawerState = depends.drawerState, content = {
        content()
    })
}