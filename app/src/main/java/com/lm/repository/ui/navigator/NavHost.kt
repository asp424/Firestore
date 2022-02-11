package com.lm.repository.ui.navigator

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.lm.repository.di.MainDep.depends
import com.lm.repository.ui.navigator.animation.*
import com.lm.repository.ui.screens.MainScreen
import com.lm.repository.ui.screens.UserInfo
import com.lm.repository.ui.screens.onmainscreen.Delivery
import com.lm.repository.ui.screens.onmainscreen.Menu
import com.lm.repository.ui.screens.onmainscreen.Restaurants
import com.lm.repository.ui.screens.onuserinfo.Addresses
import com.lm.repository.ui.screens.onuserinfo.MyProfile
import com.lm.repository.ui.screens.onuserinfo.OrdersList

@OptIn(
    ExperimentalAnimationApi::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class,
    androidx.compose.material.ExperimentalMaterialApi::class
)
@Composable
fun AnimatedNavHost() {
    AnimatedNavHost(
        navController = depends.navController,
        startDestination = screen(Screens.MainScreen)
    ) {
        composable(screen(Screens.UserInfo), enterTransition = {
            when (initialState.destination.route) {
                screen(Screens.MainScreen) -> enterDownToUp
                else -> enterLeftToRight
            }
        },
            exitTransition = {
                when (targetState.destination.route) {
                    screen(Screens.MainScreen) -> exitDownToUp
                    else -> exitRightToLeft
                }
            }) { UserInfo() }

        composable(screen(Screens.MainScreen), enterTransition = {
            when (initialState.destination.route) {
                screen(Screens.UserInfo) -> enterDownToUp
                else -> enterLeftToRight
            }
        },
            exitTransition = {
                when (targetState.destination.route) {
                    screen(Screens.UserInfo) -> exitDownToUp
                    else -> exitRightToLeft
                }
            }) { MainScreen() }

        composable(screen(Screens.MyProfile), enterTransition = { enterRightToLeft },
            exitTransition = {
                exitLeftToRight
            }) { MyProfile() }

        composable(screen(Screens.Delivery), enterTransition = { enterRightToLeft },
            exitTransition = { exitLeftToRight }) { Delivery() }

        composable(screen(Screens.Restaurants), enterTransition = { enterRightToLeft },
            exitTransition = { exitLeftToRight }) { Restaurants() }

        composable(screen(Screens.Menu), enterTransition = { enterRightToLeft },
            exitTransition = { exitLeftToRight }) { Menu() }

        composable(screen(Screens.OrdersList), enterTransition = { enterRightToLeft },
            exitTransition = { exitLeftToRight }) { OrdersList() }

        composable(screen(Screens.Addresses), enterTransition = { enterRightToLeft },
            exitTransition = { exitLeftToRight }) { Addresses() }
    }
}






