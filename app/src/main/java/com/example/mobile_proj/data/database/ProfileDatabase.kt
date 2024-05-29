package com.example.mobile_proj.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Profile::class, Workout::class], version = 4)
abstract class ProfileDatabase : RoomDatabase() {
    abstract fun profileDAO(): ProfileDAO

    abstract fun workoutDAO(): WorkoutDAO
}