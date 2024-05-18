package com.example.mobile_proj.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.mobile_proj.ui.Route

@Composable
fun BottomAppBar(navController: NavHostController) {
    BottomAppBar(actions = {
        IconButton(onClick = { navController.navigate(Route.Profile.route)}) {
            Icon(Icons.Outlined.AccountCircle, "Profile Screen")
        }
    })
}
