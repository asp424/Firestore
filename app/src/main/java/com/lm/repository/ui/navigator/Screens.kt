package com.lm.repository.ui.navigator

sealed class Screens {
    object MainScreen : Screens()
    object MyProfile : Screens()
    object UserInfo : Screens()
    object Delivery : Screens()
    object Restaurants : Screens()
    object Menu : Screens()
    object OrdersList : Screens()
    object Addresses : Screens()
}

fun screen(screens: Screens) = when (screens) {
    is Screens.MainScreen -> "MainScreen"
    is Screens.MyProfile -> "MyProfile"
    is Screens.UserInfo -> "UserInfo"
    is Screens.Delivery -> "Delivery"
    is Screens.Restaurants -> "Restaurants"
    is Screens.Menu -> "Menu"
    is Screens.OrdersList -> "OrdersList"
    is Screens.Addresses -> "Addresses"
}

