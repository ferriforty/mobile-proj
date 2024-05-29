package com.example.mobile_proj.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mobile_proj.database.Connection
import com.example.mobile_proj.ui.screens.addWorkout.AddWorkoutScreen
import com.example.mobile_proj.ui.screens.addWorkout.AddWorkoutViewModel
import com.example.mobile_proj.ui.screens.editProfile.EditProfileScreen
import com.example.mobile_proj.ui.screens.editProfile.EditProfileViewModel
import com.example.mobile_proj.ui.screens.favoriteWorkout.FavoriteScreen
import com.example.mobile_proj.ui.screens.home.HomeScreen
import com.example.mobile_proj.ui.screens.map.MapView
import com.example.mobile_proj.ui.screens.profile.ProfileScreen
import com.example.mobile_proj.ui.screens.profile.ProfileViewModel
import com.example.mobile_proj.ui.screens.schedule.ScheduleView
import com.example.mobile_proj.ui.screens.settings.SettingsScreen
import com.example.mobile_proj.ui.screens.settings.ThemeState
import com.example.mobile_proj.ui.screens.settings.ThemeViewModel
import com.example.mobile_proj.ui.screens.workoutChatBot.ChatBotScreen
import com.example.mobile_proj.ui.screens.workoutChatBot.WorkoutChatBotViewModel
import com.google.gson.Gson
import org.json.JSONObject
import org.koin.androidx.compose.koinViewModel

sealed class Route(
    val route: String,
    val title: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    data object Home : Route("travels","Gym Shred")
    data object Profile : Route("profile", "My Profile")
    data object Settings : Route("settings", "Settings")
    data object EditProfile : Route("edit-profile", "Edit Profile")
    data object ViewMap : Route("view-map", "Gym Near You")
    data object Schedule : Route("schedule", "Schedule Your Workout")

    data object AddWorkout : Route("add-workout", "New Workout")
    data object ChatBot : Route("chat-bot/{muscle-group}/{exercise}", "Chat Bot (bzz bzz)",
        listOf(navArgument("muscle-group") { type = NavType.StringType},
            navArgument("exercise") { type = NavType.StringType}
        )
    ){
        fun buildRoute(muscleGroup: String, exercise: String) = "chat-bot/$muscleGroup/$exercise"
    }
    data object FavoriteWorkout : Route("favorite-workout", "Your Favorite Workout")

    companion object {
        val routes = setOf(Home, Profile, Settings, EditProfile, AddWorkout, ChatBot, FavoriteWorkout)
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
    val workoutViewModel = koinViewModel<WorkoutViewModel>()
    val workoutState by workoutViewModel.state.collectAsStateWithLifecycle()
    val workoutChatBotViewModel = koinViewModel<WorkoutChatBotViewModel>()
    val addWorkoutViewModel = koinViewModel<AddWorkoutViewModel>()
    val addWorkoutState by addWorkoutViewModel.state.collectAsStateWithLifecycle()
    NavHost(
        navController = navController,
        startDestination = Route.Home.route,
        modifier = modifier
    ) {
        with(Route.Home) {
            composable(route) {
                HomeScreen (workoutViewModel, workoutState, navController)
            }
        }
        with(Route.Profile) {
            composable(route) {
                ProfileScreen(profileState, navController, db)
            }
        }
        with(Route.Settings) {
            composable(route) {
                SettingsScreen(navController, themeState, themeViewModel, db, context)
            }
        }
        with(Route.Schedule) {
            composable(route) {
                ScheduleView(navController, db, context)
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
            composable(route, arguments) {
                val muscleGroup = it.arguments?.getString("muscle-group")?: "No data"
                val exercise = it.arguments?.getString("exercise")?: "No data"
                ChatBotScreen(
                    navController = navController,
                    workoutChatBotViewModel = workoutChatBotViewModel,
                    state = addWorkoutState,
                    actions = addWorkoutViewModel.actions,
                    onSubmit = {
                        workoutViewModel.addWorkout(addWorkoutState.toWorkout())
                        db.insertWorkout(addWorkoutState.toWorkout())
                    },
                    muscleGroup = muscleGroup,
                    exercise = exercise,
                    db
                )
            }
        }
        with(Route.FavoriteWorkout) {
            composable(route) {
                val favoriteState by workoutViewModel.favoriteList.collectAsStateWithLifecycle()
                FavoriteScreen(workoutViewModel, favoriteState, navController)
            }
        }
    }
}