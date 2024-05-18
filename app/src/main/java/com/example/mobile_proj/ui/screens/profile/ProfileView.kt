package com.example.mobile_proj.ui.screens.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(navController: NavHostController) {
    Scaffold { contentPadding ->
        Icon(Icons.Outlined.AccountCircle,
            "Profile Image",
                Modifier.padding(contentPadding)
                    .size(45.dp)
        )
    }
}