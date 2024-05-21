package com.example.mobile_proj.utils

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

interface CameraLauncher {
    fun capturedImage()
}

@Composable
fun rememberCameraLauncher(): CameraLauncher {
    val imageUri by remember { mutableStateOf(Uri.EMPTY) }
    val cameraActivityLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { pictureTaken ->
        }
    val cameraLauncher by remember {
        derivedStateOf {
            object : CameraLauncher {
                override fun capturedImage() = cameraActivityLauncher.launch(imageUri)

            }
        }
    }
    return cameraLauncher
}