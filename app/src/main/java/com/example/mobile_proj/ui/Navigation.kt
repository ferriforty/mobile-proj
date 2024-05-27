package com.example.mobile_proj.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobile_proj.database.Connection
import com.example.mobile_proj.ui.screens.addWorkout.AddWorkoutScreen
import com.example.mobile_proj.ui.screens.editProfile.EditProfileScreen
import com.example.mobile_proj.ui.screens.editProfile.EditProfileViewModel
import com.example.mobile_proj.ui.screens.home.HomeScreen
import com.example.mobile_proj.ui.screens.map.MapView
import com.example.mobile_proj.ui.screens.profile.ProfileScreen
import com.example.mobile_proj.ui.screens.profile.ProfileViewModel
import com.example.mobile_proj.ui.screens.settings.SettingsScreen
import com.example.mobile_proj.ui.screens.settings.ThemeState
import com.example.mobile_proj.ui.screens.settings.ThemeViewModel
import com.example.mobile_proj.ui.screens.workoutChatBot.ChatBotScreen
import org.koin.androidx.compose.koinViewModel

sealed class Route(
    val route: String,
    val title: String,
) {
    data object Home : Route("travels","Gym Shred")
    data object Profile : Route("profile", "My Profile")
    data object Settings : Route("settings", "Settings")
    data object EditProfile : Route("edit-profile", "Edit Profile")
    data object ViewMap : Route("view-map", "Map")
    data object AddWorkout : Route("add-workout", "New Workout")
    data object ChatBot : Route("chat-bot", "Chat Bot (bzz bzz)")

    companion object {
        val routes = setOf(Home, Profile, Settings, EditProfile, AddWorkout, ChatBot)
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    themeState: ThemeState,
    themeViewModel: ThemeViewModel,
    db: Connection,
    context: Context
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
                SettingsScreen(navController, themeState, themeViewModel, db, context)
            }
        }
        with(Route.EditProfile) {
            composable(route) {
                val editProfileVm = koinViewModel<EditProfileViewModel>()
                val state by editProfileVm.state.collectAsStateWithLifecycle()
                EditProfileScreen(
                    profileState = profileState,
                    state = state,
                    actions = editProfileVm.actions,
                    onSubmit = { profileVm.addProfile(state.toProfile()) },
                    navController
                )
            }
        }
        with(Route.ViewMap) {
            composable(route) {
                MapView(
                    navController = navController
                )
            }
        }
        with(Route.AddWorkout) {
            composable(route) {
                AddWorkoutScreen(
                    navController = navController
                )
            }
        }
        with(Route.ChatBot) {
            composable(route) {
                ChatBotScreen(
                    navController = navController
                )
            }
        }
    }
}