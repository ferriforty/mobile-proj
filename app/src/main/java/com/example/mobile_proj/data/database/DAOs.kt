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

    @Query("UPDATE profile SET username = :username WHERE username = :username")
    suspend fun setUsername(id: Int, username: String)
    @Upsert
    suspend fun upsert(profile: Profile)
    @Delete
    suspend fun delete(profile: Profile)
}

@Dao
interface WorkoutDAO {
    @Query("SELECT * FROM workout")
    fun getWorkoutList(): Flow<List<Workout>>
    @Upsert
    suspend fun upsert(workout: Workout)
    @Delete
    suspend fun delete(workout: Workout)
}