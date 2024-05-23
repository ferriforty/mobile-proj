package com.example.mobile_proj.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobile_proj.ui.screens.editProfile.EditProfileScreen
import com.example.mobile_proj.ui.screens.editProfile.EditProfileViewModel
import com.example.mobile_proj.ui.screens.home.HomeScreen
import com.example.mobile_proj.ui.screens.profile.ProfileScreen
import com.example.mobile_proj.ui.screens.profile.ProfileViewModel
import com.example.mobile_proj.ui.screens.settings.SettingsScreen
import com.example.mobile_proj.ui.screens.settings.ThemeState
import com.example.mobile_proj.ui.screens.settings.ThemeViewModel
import org.koin.androidx.compose.koinViewModel

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
    val profileVm = koinViewModel<ProfileViewModel>()
    val profileState by profileVm.state.collectAsStateWithLifecycle()
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
                ProfileScreen(profileState, navController)
            }
        }
        with(Route.Settings) {
            composable(route) {
                SettingsScreen(navController, themeState, themeViewModel)
            }
        }
        with(Route.EditProfile) {
            composable(route) {
                val editProfileVm = koinViewModel<EditProfileViewModel>()
                val state by editProfileVm.state.collectAsStateWithLifecycle()
                EditProfileScreen(
                    state = state,
                    actions = editProfileVm.actions,
                    onSubmit = { profileVm.addProfile(state.toProfile()) },
                    navController
                )
            }
        }
    }
}