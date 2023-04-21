package com.example.projectlathidataapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.projectlathidataapp.Screens.Login.LoginScreen
import com.example.projectlathidataapp.Screens.Record.RecordScreen
import com.example.projectlathidataapp.Screens.Register.RegisterScreen
import com.example.projectlathidataapp.Screens.Splash.SplashScreen

@Composable
fun SetNavGraph(
    navController: NavHostController
){
    NavHost(navController = navController,
            startDestination = Screen.Splash.route){
            composable(
                route = Screen.Splash.route
            ){
                SplashScreen(navController = navController)
            }

            composable(
                route = Screen.Login.route
            ){
                LoginScreen(navController = navController)
            }

            composable(
                route = Screen.Register.route
            ){
                RegisterScreen(navController = navController)
            }

            composable(
                route = Screen.Record.route
            ){
                RecordScreen(navController = navController)
            }
    }
}