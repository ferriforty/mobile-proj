package com.example.mobile_proj.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profile(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo
    val username: String,

    @ColumnInfo
    val imageUri: String?
)

@Entity
data class Workout(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo
    val idRemote: String = "",

    @ColumnInfo
    val username: String,

    @ColumnInfo
    val muscleGroup: String,

    @ColumnInfo
    val exercise: String,

    @ColumnInfo
    val botchat: String,

    @ColumnInfo
    val favorite: Boolean
)