package com.example.mobile_proj.ui.screens.addWorkout

import androidx.lifecycle.ViewModel
import com.example.mobile_proj.data.database.Workout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AddWorkoutState(
    val username: String = "",
    val muscleGroup: String = "",
    val exercise: String = "",
    val botchat: String = "",
    val favorite: Boolean = false
) {
    fun toWorkout() = Workout(
        username = username,
        muscleGroup =  muscleGroup,
        exercise = exercise,
        botchat = botchat,
        favorite = favorite
    )
}

interface AddWorkoutActions {
    fun setUsername(username: String)
    fun setMuscleGroup(muscleGroup: String)
    fun setExercise(exercise: String)
    fun setBotchat(botchat: String)
    fun setFavorite(favorite: Boolean)
}

class AddWorkoutViewModel : ViewModel() {
    private val _state = MutableStateFlow(AddWorkoutState())
    val state = _state.asStateFlow()

    val actions = object : AddWorkoutActions {
        override fun setUsername(username: String) =
            _state.update { it.copy(username = username) }

        override fun setMuscleGroup(muscleGroup: String) =
            _state.update { it.copy(muscleGroup = muscleGroup) }

        override fun setExercise(exercise: String) =
            _state.update { it.copy(exercise = exercise) }

        override fun setBotchat(botchat: String) =
            _state.update { it.copy(botchat = botchat) }

        override fun setFavorite(favorite: Boolean) =
            _state.update { it.copy(favorite = favorite) }
    }
}
