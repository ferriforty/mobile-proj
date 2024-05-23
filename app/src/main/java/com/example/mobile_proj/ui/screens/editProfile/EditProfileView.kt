package com.example.mobile_proj.ui.screens.editProfile

import android.Manifest
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_proj.R
import com.example.mobile_proj.ui.Route
import com.example.mobile_proj.ui.composables.ProfileImageHolder
import com.example.mobile_proj.ui.composables.Size
import com.example.mobile_proj.ui.composables.TopAppBar
import com.example.mobile_proj.utils.rememberCameraLauncher
import com.example.mobile_proj.utils.rememberPermission

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(state: EditProfileState,
                      actions: EditProfileActions,
                      onSubmit: () -> Unit,
                      navController: NavHostController
) {
    val ctx = LocalContext.current

    val cameraLauncher = rememberCameraLauncher()

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
        topBar = { TopAppBar(navController, Route.EditProfile, null) },
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    ProfileImageHolder(uri = state.imageUri , size = Size.Lg )
                }
                IconButton(onClick = ::takePicture) {
                    Icon(painter = painterResource(id = R.drawable.ic_photo_camera),
                        "take-picture",
                        modifier = Modifier.size(64.dp))
                }
                Text("Take a picture")
                Divider(modifier = Modifier.padding(5.dp))
                OutlinedTextField(
                    value = state.username,
                    onValueChange = actions::setUsername,
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(onClick = {onSubmit()}) {
                    
                }
            }
        }
    )
}
