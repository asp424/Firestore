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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

data class Main @OptIn(
    ExperimentalCoroutinesApi::class,
    ExperimentalMaterialApi::class
) constructor(
    val fireAuth: FirebaseAuth,
    val sharedPrefProvider: SharedPrefProvider,
    val mainViewModel: MainViewModel,
    val bottomSheetState: ModalBottomSheetState,
    val drawerState: DrawerState,
    val navController: NavHostController,
    val coroutine: CoroutineScope,
    val screenWidth: Dp,
    val screenHeight: Dp,
    val swipeState: SwipeableState<Int>
)

data class Reg @OptIn(ExperimentalCoroutinesApi::class) constructor(
    val regViewModel: RegViewModel
)

val LocalMainDependencies = staticCompositionLocalOf<Main> {
    error("No value provided")
}
val LocalRegDependencies = staticCompositionLocalOf<Reg> { error("No value provided") }

@OptIn(
    ExperimentalCoroutinesApi::class, ExperimentalMaterialApi::class,
    androidx.compose.animation.ExperimentalAnimationApi::class
)
@Composable
fun MainDependencies(content: @Composable () -> Unit) {

    val coroutine = rememberCoroutineScope()

    val dispatcher = Dispatchers.IO

    val authInst = FirebaseAuth.getInstance()

    val sharedPrefProvider = SharedPrefProvider.Base(
        (LocalContext.current as MainActivity)
            .getSharedPreferences("id", MODE_PRIVATE)
    )

    val mainViewModel = ViewModelProvider(
        LocalContext.current as MainActivity,
        MainViewModelFactory(
            FirestoreRepositoryImpl(FirestoreSourceImpl(authInst)),
            dispatcher
        )
    )[MainViewModel::class.java]

    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val navController = rememberAnimatedNavController()

    val swipeState = rememberSwipeableState(0)

    val width = LocalConfiguration.current.screenWidthDp.dp

    val height = LocalConfiguration.current.screenHeightDp.dp

    val all = Main(
        fireAuth = authInst,
        sharedPrefProvider = sharedPrefProvider,
        mainViewModel = mainViewModel,
        bottomSheetState = bottomSheetState,
        drawerState = drawerState,
        navController = navController,
        coroutine = coroutine,
        screenWidth = width,
        screenHeight = height,
        swipeState = swipeState
    )
    CompositionLocalProvider(LocalMainDependencies provides all, content = content)
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun RegDependencies(content: @Composable () -> Unit) {

    val dispatcher = Dispatchers.IO

    val authInst = FirebaseAuth.getInstance()

    val authRepository = AuthRepository.Base(authInst)

    val sharedPrefProvider = SharedPrefProvider.Base(
        (LocalContext.current as MainActivity)
            .getSharedPreferences("id", MODE_PRIVATE)
    )

    val regViewModel = ViewModelProvider(
        LocalContext.current as MainActivity,
        RegViewModelFactory(
            RegRepository.Base(
                PhoneAuthOptions.newBuilder(authInst)
                    .setActivity(LocalContext.current as MainActivity), StatusCollector.Base(
                    authInst, authRepository,
                    dispatcher, sharedPrefProvider
                ), authRepository
            )
        )
    )[RegViewModel::class.java]

    CompositionLocalProvider(
        LocalRegDependencies provides Reg(regViewModel = regViewModel),
        content = content
    )
}

object MainDep {
    val depends: Main
        @Composable
        get() = LocalMainDependencies.current
}

object RegDep {
    val dependsReg: Reg
        @Composable
        get() = LocalRegDependencies.current
}

var bottomSheetContent = ""

var backScreen = "MainScreen"



