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

    @Upsert
    suspend fun upsert(profile: Profile)
    @Delete
    suspend fun delete(profile: Profile)
}