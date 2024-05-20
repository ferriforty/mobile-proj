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
import com.example.mobile_proj.ui.Route
import com.example.mobile_proj.ui.composables.TopAppBar

@Composable
fun ProfileScreen(navController: NavHostController) {
    Scaffold (
        topBar = { TopAppBar(navController, Route.Profile) }
    ){ contentPadding ->
        Icon(Icons.Outlined.AccountCircle,
            "Profile Image",
            Modifier
                .padding(contentPadding)
                .size(45.dp)
        )
    }
}