package com.example.projectlathidataapp

sealed class Screen(val route: String){
    object Splash: Screen(route = "splash_screen")
    object Login: Screen(route = "login_screen")
    object Register: Screen(route = "register_screen")
}
