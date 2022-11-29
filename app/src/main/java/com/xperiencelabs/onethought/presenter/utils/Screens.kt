package com.xperiencelabs.onethought.presenter.utils

sealed class ScreenRoutes(val route:String) {
    object SplashScreen:ScreenRoutes("splash_screen")
    object LoginScreen:ScreenRoutes("login_screen")
    object SignUpScreen:ScreenRoutes("signUp_screen")
    object HomeScreen:ScreenRoutes("home_screen")
    object ProfileScreen:ScreenRoutes("profile_screen")
    object FavoriteScreen:ScreenRoutes("favorite_screen")
    object SearchScreen:ScreenRoutes("search_screen")
    object EditProfileScreen:ScreenRoutes("edit_profile")
    object PostScreen:ScreenRoutes("post_screen")

}