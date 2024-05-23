package com.example.mobile_proj.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profile(
    @PrimaryKey
    val username: String,

    @ColumnInfo
    val imageUri: String?
)