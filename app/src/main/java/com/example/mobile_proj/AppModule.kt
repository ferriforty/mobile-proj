package com.example.mobile_proj

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.mobile_proj.data.database.ProfileDatabase
import com.example.mobile_proj.data.repositories.ProfileRepository
import com.example.mobile_proj.data.repositories.ThemeRepository
import com.example.mobile_proj.data.repositories.WorkoutRepository
import com.example.mobile_proj.ui.WorkoutViewModel
import com.example.mobile_proj.ui.screens.addWorkout.AddWorkoutViewModel
import com.example.mobile_proj.ui.screens.editProfile.EditProfileViewModel
import com.example.mobile_proj.ui.screens.profile.ProfileViewModel
import com.example.mobile_proj.ui.screens.settings.ThemeViewModel
import com.example.mobile_proj.ui.screens.workoutChatBot.WorkoutChatBotViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val Context.dataStore by preferencesDataStore("theme")

val appModule = module {
    single { get<Context>().dataStore }

    single {
        Room.databaseBuilder(
            get(),
            ProfileDatabase::class.java,
            "profile"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single { ThemeRepository(get()) }

    single {
        ProfileRepository(
            get<ProfileDatabase>().profileDAO()
        )
    }

    single {
        WorkoutRepository(
            get<ProfileDatabase>().workoutDAO()
        )
    }

    viewModel { ProfileViewModel(get()) }

    viewModel { ThemeViewModel(get()) }

    viewModel { EditProfileViewModel() }

    viewModel {WorkoutChatBotViewModel() }

    viewModel { AddWorkoutViewModel() }

    viewModel { WorkoutViewModel(get()) }
}