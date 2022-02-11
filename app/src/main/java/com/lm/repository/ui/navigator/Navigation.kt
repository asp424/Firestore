package com.lm.repository.ui.navigator

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable

@OptIn(
    ExperimentalMaterialApi::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class,
    androidx.compose.animation.ExperimentalAnimationApi::class
)
@Composable
fun Navigation() {
    Drawer {
            DrawerTopBar()
            NavHost()
            BottomSheet()
        }
}

