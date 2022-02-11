package com.lm.repository.ui.navigator

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.lm.repository.di.MainDep
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
fun NavHost() {
    MainDep.depends.apply {
        AnimatedNavHost(navController = navController, startDestination = "MainScreen") {
            composable("MyProfile", enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(500)
                )
            },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Down,
                        animationSpec = tween(500)
                    )
                })

            {
                MyProfile()
            }

            composable("MainScreen", enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up, tween(500)
                )
            },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Down,
                        animationSpec = tween(500)
                    )
                }) {
                MainScreen()
            }
            composable("UserInfo", enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up, tween(500)
                )
            },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Down,
                        animationSpec = tween(500)
                    )
                }) {
                UserInfo()
            }
            composable("Delivery", enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(500)
                )
            },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Down,
                        animationSpec = tween(500)
                    )
                }) {
                Delivery()
            }

            composable("Restaurants", enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(500)
                )
            },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Down,
                        animationSpec = tween(500)
                    )
                }) {
                Restaurants()
            }

            composable("Menu", enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(500)
                )
            },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Down,
                        animationSpec = tween(500)
                    )
                }) {
                Menu()
            }

            composable("OrdersList", enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(500)
                )
            },
                exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Down,
                        animationSpec = tween(500)
                    )
                }) {
                OrdersList()
            }
            composable("Addresses", enterTransition = { upToDown },
                exitTransition = { downToUp }) { Addresses() }
        }
    }
}



@OptIn(ExperimentalAnimationApi::class)
private val AnimatedContentScope<NavBackStackEntry>.upToDown
get() = slideIntoContainer(AnimatedContentScope.SlideDirection.Down, tween(500))

@OptIn(ExperimentalAnimationApi::class)
private val AnimatedContentScope<NavBackStackEntry>.downToUp
    get() = slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(500))



