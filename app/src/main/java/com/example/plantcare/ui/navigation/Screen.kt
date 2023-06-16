package com.example.plantcare.ui.navigation

sealed class Screen (val route : String) {
    object Splash : Screen("splash")
    object Permission : Screen("permission")
    object Diagnoses : Screen("diagnosis")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Scan : Screen("scan")
    object ScanApple : Screen("scan_apple")
    object History : Screen("history")
    object Camera : Screen("camera")
    object Profile : Screen("profile")
    object Detail: Screen("home/{categoryId}") {
        fun createRoute(categoryId: Int) = "home/$categoryId"
    }
}