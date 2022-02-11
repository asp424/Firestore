package com.lm.repository.ui.navigator

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.lm.repository.di.MainDep.depends

@OptIn(
    ExperimentalMaterialApi::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class,
    androidx.compose.animation.ExperimentalAnimationApi::class
)
@Composable
fun Navigation() {
    LocalLifecycleOwner.current.lifecycle.addObserver(depends.mainViewModel)
    Drawer {
            DrawerTopBar()
            AnimatedNavHost()
            BottomSheet()
        }
}

