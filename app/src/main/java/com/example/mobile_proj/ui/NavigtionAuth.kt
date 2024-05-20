package com.example.mobile_proj.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobile_proj.database.Connection
import com.example.mobile_proj.ui.screens.auth.SignIn
import com.example.mobile_proj.ui.screens.auth.SignUp

sealed class RouteAuth(
    val route: String,
    val title: String,
) {
    data object SignIn : Route("signIn", "Sign In")
    data object SignUp : Route("signUp", "Sign Up")

    companion object {
        val routes = setOf(SignIn, SignUp)
    }
}

@Composable
fun NavGraphAuth(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    db: Connection
) {
    NavHost(
        navController = navController,
        startDestination = RouteAuth.SignIn.route,
        modifier = modifier
    ) {
        with(RouteAuth.SignIn) {
            composable(route) {
                SignIn(navController, db)
            }
        }
        with(RouteAuth.SignUp) {
            composable(route) {
                SignUp(navController, db)
            }
        }
    }
}