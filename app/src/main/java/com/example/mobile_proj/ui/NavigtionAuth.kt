package com.example.mobile_proj.ui

import android.content.Context
import android.content.Intent
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
    data object SignInRoute : Route("signIn", "Sign In")
    data object SignUpRoute : Route("signUp", "Sign Up")

    companion object {
        val routes = setOf(SignInRoute, SignUpRoute)
    }
}

@Composable
fun NavGraphAuth(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    db: Connection,
    intent: Intent
) {
    NavHost(
        navController = navController,
        startDestination = RouteAuth.SignInRoute.route,
        modifier = modifier
    ) {
        with(RouteAuth.SignInRoute) {
            composable(route) {
                SignIn(navController, db, intent)
            }
        }
        with(RouteAuth.SignUpRoute) {
            composable(route) {
                SignUp(navController, db, intent)
            }
        }
    }
}