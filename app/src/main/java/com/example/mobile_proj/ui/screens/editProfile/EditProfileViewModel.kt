package com.example.mobile_proj.ui.screens.editProfile

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.mobile_proj.data.database.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class EditProfileState(
    val username: String = "",
    val imageUri: Uri =  Uri.EMPTY,
    val oldPassword: String = "",
    val password: String = ""
) {
    fun toProfile() = Profile(
        username = username,
        imageUri = imageUri.toString()
    )
}

interface EditProfileActions {
    fun setOldPassword(oldPassword: String)
    fun setPassword(password: String)
    fun setUsername(username: String)
    fun setImageUri(imageUri: Uri)
}

class EditProfileViewModel : ViewModel() {
    private val _state = MutableStateFlow(EditProfileState())
    val state = _state.asStateFlow()

    val actions = object : EditProfileActions {
        override fun setUsername(username: String) =
            _state.update { it.copy(username = username) }

        override fun setImageUri(imageUri: Uri) =
            _state.update { it.copy(imageUri = imageUri) }

        override fun setOldPassword(oldPassword: String) =
            _state.update { it.copy(oldPassword = oldPassword) }

        override fun setPassword(password: String) =
            _state.update { it.copy(password = password) }
    }
}
