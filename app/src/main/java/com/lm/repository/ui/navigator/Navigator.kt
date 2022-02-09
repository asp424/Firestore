package com.lm.repository.ui.navigator

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.FirebaseAuth
import com.lm.repository.core.SharedPrefProvider
import com.lm.repository.ui.screens.MainScreen
import com.lm.repository.ui.screens.MyProfile
import com.lm.repository.ui.screens.UserInfo
import com.lm.repository.ui.screens.onmainscreen.*
import com.lm.repository.ui.viewmodels.MainViewModel
import com.lm.repository.ui.viewmodels.RegViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
@Composable
fun Navigator(
    mVm: MainViewModel,
    rVm: RegViewModel,
    sharedPreferences: SharedPrefProvider,
    auth: FirebaseAuth
) {
    val navController = rememberAnimatedNavController()

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
            MyProfile(mVm, sharedPreferences, navController)
        }

        composable("MainScreen", enterTransition = {
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
            MainScreen(
                mainViewModel = mVm,
                regViewModel = rVm,
                sharedPreferences = sharedPreferences,
                firebaseAuth = auth,
                navController
            )
        }
        composable("UserInfo", enterTransition = {
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
            UserInfo(mVm, sharedPreferences, navController, rVm)
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
            }){
            Delivery(mVm, auth, navController, rVm, sharedPreferences)
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
            }){
            Restaurants(mVm, auth, navController, rVm, sharedPreferences)
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
            }){
            Menu(mVm, auth, navController, rVm, sharedPreferences)
        }

        composable("Booking", enterTransition = {
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
            }){
            Booking(mVm, auth, navController, rVm, sharedPreferences)
        }

        composable("BonusCard", enterTransition = {
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
            }){
            BonusCard(mVm, auth, navController, rVm, sharedPreferences)
        }
    }
}

