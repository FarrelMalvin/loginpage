package com.example.loginpage

sealed class Screen (val route : String) {
    object Mainscreen : Screen(route = "main_screen")
    object WelcomeScreen : Screen(route = "welcome_screen")
}