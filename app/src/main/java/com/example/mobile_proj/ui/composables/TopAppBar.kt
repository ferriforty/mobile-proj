package com.example.mobile_proj.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.mobile_proj.ui.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    navController: NavHostController,
    currentRoute: Route
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                currentRoute.title,
                fontWeight = FontWeight.Medium,
            )
        },
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Back button"
                    )
                }
            }
        },
        actions = {
            if (currentRoute.route == Route.Home.route) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Outlined.Search, contentDescription = "Search")
                }
            }
            if (currentRoute.route == Route.Home.route) {
                IconButton(onClick = { navController.navigate(Route.Settings.route) }) {
                    Icon(Icons.Outlined.Settings, "Settings")
                }
            }
            if (currentRoute.route == Route.Profile.route) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Outlined.Edit, contentDescription = "Edit Profile")
            }
        }

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}