package com.example.mobile_proj

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.mobile_proj.data.database.ProfileDatabase
import com.example.mobile_proj.data.repositories.ProfileRepository
import com.example.mobile_proj.data.repositories.ThemeRepository
import com.example.mobile_proj.ui.screens.editProfile.EditProfileViewModel
import com.example.mobile_proj.ui.screens.profile.ProfileViewModel
import com.example.mobile_proj.ui.screens.settings.ThemeViewModel
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
            get<ProfileDatabase>().profileDAO(),
            get<Context>().applicationContext.contentResolver
        )
    }

    viewModel { ProfileViewModel(get()) }

    viewModel { ThemeViewModel(get()) }

    viewModel { EditProfileViewModel() }
}