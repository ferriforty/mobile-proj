package com.example.mobile_proj.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_proj.data.database.Profile
import com.example.mobile_proj.data.repositories.ProfileRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ProfileState(val profile: List<Profile>)

class ProfileViewModel(
    private val repository: ProfileRepository
) : ViewModel() {

    val state = repository.profile.map { ProfileState( profile = it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ProfileState(emptyList())
    )

    fun addProfile(profile: Profile) = viewModelScope.launch {
        repository.upsert(profile)
    }

    fun deletePlace(profile: Profile) = viewModelScope.launch {
        repository.delete(profile)
    }
}