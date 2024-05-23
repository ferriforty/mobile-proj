package com.example.mobile_proj.data.repositories

import android.content.ContentResolver
import android.net.Uri
import com.example.mobile_proj.data.database.Profile
import com.example.mobile_proj.data.database.ProfileDAO
import com.example.mobile_proj.utils.saveImageToStorage
import kotlinx.coroutines.flow.Flow

class ProfileRepository(
    private val profileDAO: ProfileDAO,
    private val contentResolver: ContentResolver
) {
    val profile: Flow<List<Profile>> = profileDAO.getProfile()
    suspend fun upsert(profile: Profile) {
        if (profile.imageUri?.isNotEmpty() == true) {
            val imageUri = saveImageToStorage(
                Uri.parse(profile.imageUri),
                contentResolver,
                "Profile"
            )
            profileDAO.upsert(profile.copy(imageUri = imageUri.toString()))
        } else {
            profileDAO.upsert(profile)
        }
    }

    suspend fun delete(profile: Profile) = profileDAO.delete(profile)
}
