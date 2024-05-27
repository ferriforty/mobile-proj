package com.example.mobile_proj.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_proj.ui.Route

@Composable
fun BottomAppBar(navController: NavHostController) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        actions = {
            Spacer(modifier = Modifier.width(20.dp))
            IconButton(
                onClick = { navController.navigate(Route.Profile.route) }) {
                Icon(Icons.Outlined.DateRange,
                    "Calendar",
                    modifier = Modifier.size(48.dp)
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            IconButton(
                onClick = { navController.navigate(Route.ViewMap.route) }) {
                Icon(Icons.Outlined.LocationOn,
                    "GPS",
                    modifier = Modifier.size(48.dp)
                )
            }
            Spacer(modifier = Modifier.width(110.dp))
            IconButton(
                onClick = { navController.navigate(Route.Profile.route) }) {
                Icon(Icons.Outlined.Favorite,
                    "Favorite",
                    modifier = Modifier.size(48.dp)
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            IconButton(
                onClick = { navController.navigate(Route.Profile.route) }) {
                Icon(Icons.Outlined.AccountCircle,
                    "Profile Screen",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    )
}
