package com.example.mobile_proj.ui.screens.editProfile

import android.Manifest
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SubdirectoryArrowRight
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_proj.R
import com.example.mobile_proj.ui.Route
import com.example.mobile_proj.ui.composables.ProfileImageHolder
import com.example.mobile_proj.ui.composables.Size
import com.example.mobile_proj.ui.composables.TopAppBar
import com.example.mobile_proj.ui.screens.profile.ProfileState
import com.example.mobile_proj.utils.rememberCameraLauncher
import com.example.mobile_proj.utils.rememberPermission

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    profileState: ProfileState,
    state: EditProfileState,
    actions: EditProfileActions,
    onSubmit: () -> Unit,
    navController: NavHostController
) {
    val ctx = LocalContext.current

    val cameraLauncher = rememberCameraLauncher { imageUri ->
        actions.setImageUri(imageUri)
    }

    val cameraPermission = rememberPermission(Manifest.permission.CAMERA) { status ->
        if (status.isGranted) {
            cameraLauncher.captureImage()
        } else {
            Toast.makeText(ctx, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }
    fun takePicture() =
        if (cameraPermission.status.isGranted) {
            cameraLauncher.captureImage()
        } else {
            cameraPermission.launchPermissionRequest()
        }

    Scaffold (
        topBar = { TopAppBar(navController, Route.EditProfile, null) }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(12.dp))
            ProfileImageHolder(null, Size.Lg)
            IconButton(onClick = ::takePicture) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_photo_camera),
                    "take-picture",
                    modifier = Modifier.size(64.dp)
                )
            }
            Text("Take a picture")
            Divider(modifier = Modifier.padding(10.dp))

            var isOldPasswordVisible by remember { mutableStateOf(false) }
            var isPasswordVisible by remember { mutableStateOf(false) }

            val leadingOldIcon = @Composable {
                IconButton(onClick = { isOldPasswordVisible = !isOldPasswordVisible }) {
                    Icon(
                        if (isOldPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            val leadingIcon = @Composable {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            val focusManager = LocalFocusManager.current
            OutlinedTextField(
                value = state.oldPassword,
                onValueChange = actions::setOldPassword,
                label = { Text("Current password: ") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                leadingIcon = leadingOldIcon,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                visualTransformation = if (isOldPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )

            OutlinedTextField(
                value = state.password,
                onValueChange = actions::setPassword,
                label = { Text("New password: ") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onSubmit()
                        navController.navigateUp()
                    }
                ),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                leadingIcon = leadingIcon,
                trailingIcon = @Composable {
                    IconButton(
                        onClick =  {
                            onSubmit()
                            navController.navigateUp()
                    }) {
                        Icon(
                            Icons.Default.SubdirectoryArrowRight,
                            contentDescription = "submit change",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
            Divider(modifier = Modifier.padding(10.dp))
        }
    }
}
