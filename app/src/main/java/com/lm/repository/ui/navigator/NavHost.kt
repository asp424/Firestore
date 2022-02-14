package com.lm.repository.ui.navigator

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.lm.repository.di.MainDep.depends
import com.lm.repository.ui.navigator.animation.*
import com.lm.repository.ui.screens.MainScreen
import com.lm.repository.ui.screens.UserInfo
import com.lm.repository.ui.screens.ondrawer.About
import com.lm.repository.ui.screens.ondrawer.Events
import com.lm.repository.ui.screens.ondrawer.Promotions
import com.lm.repository.ui.screens.ondrawer.Reference
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
                screen(Screens.Delivery) -> enterDownToUp
                screen(Screens.Menu) -> enterDownToUp
                screen(Screens.Restaurants) -> enterDownToUp
                else -> enterLeftToRight
            }
        },
            exitTransition = {
                when (targetState.destination.route) {
                    screen(Screens.MainScreen) -> exitDownToUp
                    screen(Screens.Delivery) -> exitDownToUp
                    screen(Screens.Menu) -> exitDownToUp
                    screen(Screens.Restaurants) -> exitDownToUp
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

        composable(screen(Screens.Delivery), enterTransition = {
            when (initialState.destination.route) {
                screen(Screens.UserInfo) -> enterDownToUp
                else -> enterRightToLeft
            }
        },
            exitTransition = {
                when (targetState.destination.route) {
                    screen(Screens.UserInfo) -> exitDownToUp
                    else -> exitLeftToRight
                }
            }) { Delivery() }

        composable(screen(Screens.Restaurants), enterTransition = {
            when (initialState.destination.route) {
                screen(Screens.UserInfo) -> enterDownToUp
                else -> enterRightToLeft
            }
        },
            exitTransition = {
                when (targetState.destination.route) {
                    screen(Screens.UserInfo) -> exitDownToUp
                    else -> exitLeftToRight
                }
            }) { Restaurants() }

        composable(screen(Screens.About), enterTransition = {
            when (initialState.destination.route) {
                screen(Screens.UserInfo) -> enterDownToUp
                else -> enterRightToLeft
            }
        },
            exitTransition = {
                when (targetState.destination.route) {
                    screen(Screens.UserInfo) -> exitDownToUp
                    else -> exitLeftToRight
                }
            }) { About() }

        composable(screen(Screens.Promotions), enterTransition = {
            when (initialState.destination.route) {
                screen(Screens.UserInfo) -> enterDownToUp
                else -> enterRightToLeft
            }
        },
            exitTransition = {
                when (targetState.destination.route) {
                    screen(Screens.UserInfo) -> exitDownToUp
                    else -> exitLeftToRight
                }
            }) { Promotions() }

        composable(screen(Screens.Reference), enterTransition = {
            when (initialState.destination.route) {
                screen(Screens.UserInfo) -> enterDownToUp
                else -> enterRightToLeft
            }
        },
            exitTransition = {
                when (targetState.destination.route) {
                    screen(Screens.UserInfo) -> exitDownToUp
                    else -> exitLeftToRight
                }
            }) { Reference() }

        composable(screen(Screens.Events), enterTransition = {
            when (initialState.destination.route) {
                screen(Screens.UserInfo) -> enterDownToUp
                else -> enterRightToLeft
            }
        },
            exitTransition = {
                when (targetState.destination.route) {
                    screen(Screens.UserInfo) -> exitDownToUp
                    else -> exitLeftToRight
                }
            }) { Events() }

        composable(screen(Screens.Menu), enterTransition = {
            when (initialState.destination.route) {
                screen(Screens.UserInfo) -> enterDownToUp
                else -> enterRightToLeft
            }
        },
            exitTransition = {
                when (targetState.destination.route) {
                    screen(Screens.UserInfo) -> exitDownToUp
                    else -> exitLeftToRight
                }
            }) { Menu() }

        composable(screen(Screens.OrdersList), enterTransition = { enterRightToLeft },
            exitTransition = { exitLeftToRight }) { OrdersList() }

        composable(screen(Screens.Addresses), enterTransition = { enterRightToLeft },
            exitTransition = { exitLeftToRight }) { Addresses() }
    }
}






