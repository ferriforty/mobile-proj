package com.example.mobile_proj.data.repositories

import com.example.mobile_proj.data.database.Workout
import com.example.mobile_proj.data.database.WorkoutDAO
import kotlinx.coroutines.flow.Flow

class WorkoutRepository(
    private val workoutDAO: WorkoutDAO,
) {
    val workout: Flow<List<Workout>> = workoutDAO.getWorkoutList()

    val favoriteWorkout: Flow<List<Workout>> = workoutDAO.getFavoriteWorkoutList()

    suspend fun setFavorite(id: Int, favorite: Boolean) = workoutDAO.setFavorite(id, favorite)
    suspend fun getWorkout() = workoutDAO.getWorkout()
    suspend fun upsert(workout: Workout) = workoutDAO.upsert(workout)
    suspend fun delete(workout: Workout) = workoutDAO.delete(workout)
}