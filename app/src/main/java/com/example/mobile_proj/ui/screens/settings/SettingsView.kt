package com.example.mobile_proj.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_proj.R
import com.example.mobile_proj.data.models.Theme
import com.example.mobile_proj.ui.Route
import com.example.mobile_proj.ui.composables.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController, themeState: ThemeState, themeViewModel: ThemeViewModel) {
    Column {
        TopAppBar(navController, Route.Settings)
        ThemeChangeRow(themeState, themeViewModel::changeTheme)
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
fun ThemeChangeRow(state: ThemeState,  onThemeSelected: (theme: Theme) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text("Change Theme")
            Divider(
                modifier = Modifier.padding(5.dp)
            )
            Column(Modifier.selectableGroup()) {
                Theme.entries.forEach { theme->
                    Row(
                        Modifier
                            .height(56.dp)
                            .selectable(
                                selected = (theme == state.theme),
                                onClick = { onThemeSelected(theme) },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        when (theme) {
                            Theme.Light -> Icon(painter = painterResource(id = R.drawable.ic_light_theme),
                                contentDescription = "light-theme-icon",
                                modifier = Modifier.size(30.dp))
                            Theme.Dark -> Icon(painter = painterResource(id = R.drawable.ic_dark_theme),
                                contentDescription = "dark-theme-icon",
                                modifier = Modifier.size(30.dp))
                            else -> {
                                Icon(painter = painterResource(id = R.drawable.ic_system_theme),
                                    contentDescription = "dark-theme-icon",
                                    modifier = Modifier.size(30.dp))
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = theme.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        Spacer(modifier = Modifier.weight(0.1f))
                        RadioButton(
                            selected = (theme == state.theme), onClick = null)
                    }
                }
                Divider(
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}


