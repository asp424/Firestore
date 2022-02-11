package com.lm.repository.ui.utils

import androidx.compose.animation.core.tween
import androidx.compose.material.*
import com.lm.repository.di.bottomSheetContent
import com.lm.repository.ui.viewmodels.MainViewModel

@OptIn(ExperimentalMaterialApi::class)
suspend fun backAction(
    bottomSheetState: ModalBottomSheetState, drawerState: DrawerState,
    action: () -> Unit
) {
    bottomSheetState.apply {
        drawerState.also {
            if (!isVisible && !it.isOpen) action()
            if (isVisible && !isAnimationRunning) animateTo(
                ModalBottomSheetValue.Hidden, tween(600)
            )
            if (it.isOpen && !isAnimationRunning)
                it.animateTo(DrawerValue.Closed, tween(700))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
fun expandBottomSheet(mainViewModel: MainViewModel, screen: String) {
    bottomSheetContent = screen
    mainViewModel.bottomSheetAction()
}
