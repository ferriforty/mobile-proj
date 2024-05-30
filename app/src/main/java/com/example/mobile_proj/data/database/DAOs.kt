package com.example.mobile_proj.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDAO {
    @Query("SELECT * FROM profile")
    fun getProfile(): Flow<List<Profile>>

    @Query("UPDATE profile SET username = :username WHERE id = :id")
    suspend fun setUsername(id: Int, username: String)
    @Upsert
    suspend fun upsert(profile: Profile)
    @Delete
    suspend fun delete(profile: Profile)
}

@Dao
interface WorkoutDAO {
    @Query("SELECT * FROM workout ORDER BY id DESC")
    fun getWorkoutList(): Flow<List<Workout>>
    @Query("SELECT * FROM workout ORDER BY id DESC")
    suspend fun getWorkout(): List<Workout>
    @Query("SELECT * FROM workout WHERE favorite = 1")
    fun getFavoriteWorkoutList(): Flow<List<Workout>>
    @Query("UPDATE workout SET favorite = :favorite WHERE id = :id")
    suspend fun setFavorite(id: Int, favorite: Boolean)
    @Upsert
    suspend fun upsert(workout: Workout)
    @Delete
    suspend fun delete(workout: Workout)
}