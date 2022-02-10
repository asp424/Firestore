package com.lm.repository.ui.cells

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material.DrawerState
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.firebase.auth.FirebaseAuth
import com.lm.repository.core.SharedPrefProvider
import com.lm.repository.ui.screens.MainScreen
import com.lm.repository.ui.screens.onuserinfo.MyProfile
import com.lm.repository.ui.screens.UserInfo
import com.lm.repository.ui.screens.onmainscreen.Delivery
import com.lm.repository.ui.screens.onmainscreen.Menu
import com.lm.repository.ui.screens.onmainscreen.Restaurants
import com.lm.repository.ui.screens.onuserinfo.Addresses
import com.lm.repository.ui.screens.onuserinfo.OrdersList
import com.lm.repository.ui.viewmodels.MainViewModel
import com.lm.repository.ui.viewmodels.RegViewModel

@OptIn(ExperimentalAnimationApi::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class,
    androidx.compose.material.ExperimentalMaterialApi::class
)
@Composable
fun NavHost(
    navController: NavHostController,
    mVm: MainViewModel,
    rVm: RegViewModel,
    sharedPreferences: SharedPrefProvider,
    auth: FirebaseAuth,
    bottomSheetState: ModalBottomSheetState,
    drawerState: DrawerState,
    bCallback: () -> Unit
) {
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
                AnimatedContentScope.SlideDirection.Up, tween(500)
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
                navController,
                bottomSheetState,
                drawerState
            ){ bCallback() }
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
            Delivery(mVm, auth, navController, rVm, sharedPreferences,
                bottomSheetState, drawerState)
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
            Restaurants(mVm, auth, navController, rVm, sharedPreferences,
                bottomSheetState, drawerState)
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
            Menu(mVm, auth, navController, rVm, sharedPreferences,
                bottomSheetState, drawerState)
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
            }){
            OrdersList(mVm, auth, navController, rVm, sharedPreferences)
        }
        composable("Addresses", enterTransition = {
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
            Addresses(mVm, auth, navController, rVm, sharedPreferences)
        }
    }
}