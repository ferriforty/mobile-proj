package com.example.mobile_proj.data.repositories

import android.content.ContentResolver
import com.example.mobile_proj.data.database.Profile
import com.example.mobile_proj.data.database.ProfileDAO
import kotlinx.coroutines.flow.Flow

class ProfileRepository(
    private val profileDAO: ProfileDAO,
    private val contentResolver: ContentResolver
) {
    val profile: Flow<List<Profile>> = profileDAO.getProfile()
    suspend fun getUsernames(): List<String> = profileDAO.getUsernameList()

    suspend fun upsert(profile: Profile) = profileDAO.upsert(profile)
    suspend fun setUserImage(imageUri: String, username: String) = profileDAO.setUserImage(imageUri, username)

    /*suspend fun upsert(profile: Profile) {
        if (profile.imageUri?.isNotEmpty() == true) {
            val imageUri = saveImageToStorage(
                Uri.parse(profile.imageUri),
                contentResolver,
                ""
            )
            profileDAO.upsert(profile.copy(imageUri = imageUri.toString()))
        } else {
            profileDAO.upsert(profile)
        }
    }*/
}
