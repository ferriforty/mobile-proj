package com.example.mobile_proj.ui.screens.editProfile

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.mobile_proj.data.database.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class EditProfileState(
    val username: String = "",
    val imageUri: Uri = Uri.EMPTY
) {
    fun toProfile() = Profile(
        username = username,
        imageUri = imageUri.toString()
    )
}

interface EditProfileActions {
    fun setUsername(username: String)
    fun setImageUri(imageUri: Uri)
}

class EditProfileViewModel : ViewModel() {
    private val _state = MutableStateFlow(EditProfileState())
    val state = _state.asStateFlow()

    val actions = object : EditProfileActions {
        override fun setUsername(username: String) =
            _state.update { it.copy() }

        override fun setImageUri(imageUri: Uri) =
            _state.update { it.copy(imageUri = imageUri) }
    }
}
