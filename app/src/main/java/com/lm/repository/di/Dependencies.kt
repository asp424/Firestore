package com.lm.repository.di

import android.content.Context.MODE_PRIVATE
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.lm.repository.MainActivity
import com.lm.repository.core.SharedPrefProvider
import com.lm.repository.data.repository.firestore.FirestoreRepositoryImpl
import com.lm.repository.data.repository.registration.AuthRepository
import com.lm.repository.data.repository.registration.RegRepository
import com.lm.repository.data.repository.registration.StatusCollector
import com.lm.repository.data.sources.firestoresource.FirestoreSourceImpl
import com.lm.repository.ui.viewmodels.MainViewModel
import com.lm.repository.ui.viewmodels.RegViewModel
import com.lm.repository.ui.viewmodelsfactory.MainViewModelFactory
import com.lm.repository.ui.viewmodelsfactory.RegViewModelFactory
import kotlinx.coroutines.AbstractCoroutine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

data class Depends @OptIn(ExperimentalCoroutinesApi::class,
    ExperimentalMaterialApi::class
) constructor(
    val fireAuth: FirebaseAuth,
    val sharedPrefProvider: SharedPrefProvider,
    val mainViewModel: MainViewModel,
    val regViewModel: RegViewModel,
    val bottomSheetState: ModalBottomSheetState,
    val drawerState: DrawerState,
    val navController: NavHostController,
    val coroutine: CoroutineScope,
    val screenWidth: Dp,
    val screenHeight: Dp
    )

val LocalDependencies = staticCompositionLocalOf<Depends> { error("No value provided") }

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalMaterialApi::class,
    androidx.compose.animation.ExperimentalAnimationApi::class
)
@Composable
fun MainTheme(content: @Composable () -> Unit) {

    val coroutine = rememberCoroutineScope()

    val dispatcher = Dispatchers.IO

    val authInst = FirebaseAuth.getInstance()

    val authRepository = AuthRepository.Base(authInst)

    val sharedPrefProvider = SharedPrefProvider.Base((LocalContext.current as MainActivity)
        .getSharedPreferences("id", MODE_PRIVATE))

    val regViewModel = ViewModelProvider(LocalContext.current as MainActivity,
        RegViewModelFactory(RegRepository.Base(PhoneAuthOptions.newBuilder(authInst)
            .setActivity(LocalContext.current as MainActivity), StatusCollector.Base(
            authInst, authRepository,
            dispatcher, sharedPrefProvider), authRepository)))[RegViewModel::class.java]

    val mainViewModel = ViewModelProvider(LocalContext.current as MainActivity,
        MainViewModelFactory(FirestoreRepositoryImpl(FirestoreSourceImpl()),
            dispatcher))[MainViewModel::class.java]

    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val navController = rememberAnimatedNavController()

    val width = LocalConfiguration.current.screenWidthDp.dp

    val height = LocalConfiguration.current.screenHeightDp.dp

    val all = Depends(
        fireAuth = authInst,
        sharedPrefProvider = sharedPrefProvider,
        mainViewModel = mainViewModel,
        regViewModel = regViewModel,
        bottomSheetState = bottomSheetState,
        drawerState = drawerState,
        navController = navController,
        coroutine = coroutine,
        screenWidth = width,
        screenHeight = height
    )
    CompositionLocalProvider(LocalDependencies provides all, content = content)
}

object MainDep {
    val depends: Depends
        @Composable
        get() = LocalDependencies.current
}

var bottomSheetContent = ""

var backScreen = "MainScreen"



