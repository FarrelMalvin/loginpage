package com.example.loginpage

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// --- Navigation Host ---
// This is the central point for managing app navigation and state.
@Composable
fun Navigation() {
    val navController = rememberNavController()

    // State is "hoisted" here to be controlled by the Navigation component.
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginMessage by remember { mutableStateOf("") }

    val correctUsername = "user"
    val correctPassword = "12345"

    NavHost(navController = navController, startDestination = "login") {
        // Login Screen Route
        composable("login") {
            LoginScreen(
                username = username,
                password = password,
                loginMessage = loginMessage,
                onUsernameChange = { username = it },
                onPasswordChange = { password = it },
                onLoginClick = {
                    if (username.compareTo(correctUsername) == 0 && password.compareTo(correctPassword) == 0) {
                        loginMessage = "Login Berhasil "
                        navController.navigate("welcome/$username") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        loginMessage = "Login Gagal Username atau Password Salah"
                    }
                }
            )
        }


        composable("welcome/{username}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("username") ?: "User"
            WelcomeScreen(
                username = name,
                onLogoutClick = {
                    username = ""
                    password = ""
                    loginMessage = ""
                    navController.navigate("login") {
                        popUpTo("welcome/$name") { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
fun LoginScreen(
    username: String,
    password: String,
    loginMessage: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = onUsernameChange,
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))


        if (loginMessage.isNotEmpty()) {
            Text(text = loginMessage)
        }
    }
}


@Composable
fun WelcomeScreen(
    username: String,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome, $username! 👋",
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onLogoutClick) {
            Text("Logout")
        }
    }
}