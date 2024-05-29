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
    suspend fun setImageUri(id: Int, imageUri: String){
        val uri = saveImageToStorage(Uri.parse(imageUri),
            contentResolver,
            "Profile-img"
        )
       profileDAO.setImageUri(id, uri.toString())
    }

    suspend fun setUsername(id: Int, username: String) = profileDAO.setUsername(id, username)
    suspend fun upsert(profile: Profile) {
        if (profile.imageUri?.isNotEmpty() == true) {
            val imageUri = saveImageToStorage(
                Uri.parse(profile.imageUri),
                contentResolver,
                "Profile-img"
            )
            profileDAO.upsert(profile.copy(imageUri = imageUri.toString()))
        }
    }

    suspend fun delete(profile: Profile) = profileDAO.delete(profile)
}
