package com.example.mobile_proj.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mobile_proj.ui.screens.home.HomeScreen
import com.example.mobile_proj.ui.screens.profile.ProfileScreen
import com.example.mobile_proj.ui.screens.settings.SettingsScreen
import org.koin.androidx.compose.koinViewModel

sealed class Route(
    val route: String,
    val title: String,
) {
    data object Home : Route("travels", "Gym Shred")
    data object Profile : Route("profile", "My Profile")
    data object Settings : Route("settings", "Settings")

    companion object {
        val routes = setOf(Home, Profile, Settings)
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Route.Home.route,
        modifier = modifier
    ) {
        with(Route.Home) {
            composable(route) {
                HomeScreen(navController)
            }
        }
        with(Route.Profile) {
            composable(route) {
                ProfileScreen(navController)
            }
        }
        with(Route.Settings) {
            composable(route) {
                SettingsScreen(navController)
            }
        }
    }
}