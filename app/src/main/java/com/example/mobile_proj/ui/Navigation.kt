package com.example.mobile_proj.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobile_proj.ui.screens.editProfile.EditProfileScreen
import com.example.mobile_proj.ui.screens.home.HomeScreen
import com.example.mobile_proj.ui.screens.profile.ProfileScreen
import com.example.mobile_proj.ui.screens.settings.SettingsScreen
import com.example.mobile_proj.ui.screens.settings.ThemeState
import com.example.mobile_proj.ui.screens.settings.ThemeViewModel

sealed class Route(
    val route: String,
    val title: String,
) {
    data object Home : Route("travels", "Gym Shred")
    data object Profile : Route("profile", "My Profile")
    data object Settings : Route("settings", "Settings")
    data object EditProfile : Route("edit-profile", "Edit Profile")

    companion object {
        val routes = setOf(Home, Profile, Settings, EditProfile)
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    themeState: ThemeState,
    themeViewModel: ThemeViewModel
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
                SettingsScreen(navController, themeState, themeViewModel)
            }
        }
        with(Route.EditProfile) {
            composable(route) {
                EditProfileScreen(navController)
            }
        }
    }
}