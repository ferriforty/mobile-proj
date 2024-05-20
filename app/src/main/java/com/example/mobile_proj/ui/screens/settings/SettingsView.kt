package com.example.mobile_proj.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_proj.ui.Route
import com.example.mobile_proj.ui.composables.TopAppBar

@Composable
fun SettingsScreen(navController: NavHostController) {
    Column {
        TopAppBar(navController, Route.Settings)
        ThemeChangeRow()
        SettingsRow(Icons.Outlined.AccountCircle, "Log Out", Icons.Outlined.ExitToApp)
    }
}

@Composable
fun SettingsRow(imageVector: ImageVector, contentDescription: String, imageVector2: ImageVector) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector,
                        contentDescription,
                        Modifier.size(45.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(contentDescription)
                }
                Spacer(modifier = Modifier.weight(1.0f))
                Icon(imageVector2,
                    contentDescription,
                    Modifier.size(45.dp)
                )
            }
            Divider(
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Composable
fun ThemeChangeRow() {
    var check by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Settings,
                    "Change your Theme",
                    Modifier.size(45.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text("Change your Theme")
                Spacer(modifier = Modifier.weight(1.0f))
                Switch(checked = check, onCheckedChange = {check = !check} )
            }
            Divider(
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}


