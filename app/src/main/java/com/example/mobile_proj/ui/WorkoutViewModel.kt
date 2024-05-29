package com.example.mobile_proj.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_proj.data.database.Workout
import com.example.mobile_proj.data.repositories.WorkoutRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


data class WorkoutState(val workout: List<Workout>)

class WorkoutViewModel(
    private val repository: WorkoutRepository
) : ViewModel() {
    val state = repository.workout.map { WorkoutState(workout = it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = WorkoutState(emptyList())
    )
    val favoriteList = repository.favoriteWorkout.map { WorkoutState(workout = it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = WorkoutState(emptyList())
    )
    fun setFavorite(id: Int, favorite: Boolean) = viewModelScope.launch {
        repository.setFavorite(id, favorite)
    }
    fun addWorkout(workout: Workout) = viewModelScope.launch {
        repository.upsert(workout)
    }

    fun deleteWorkout(workout: Workout) = viewModelScope.launch {
        repository.delete(workout)
    }
}