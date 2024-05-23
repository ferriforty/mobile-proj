package com.example.mobile_proj.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.example.mobile_proj.ui.Route
import com.example.mobile_proj.ui.composables.ProfileImageHolder
import com.example.mobile_proj.ui.composables.Size
import com.example.mobile_proj.ui.composables.TopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(profileState: ProfileState,
                  navController: NavHostController) {
    Scaffold (
        topBar = { TopAppBar(navController, Route.Profile, null) },
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                horizontalAlignment = Alignment.Start
            ) {
                Row {
                    ProfileImageHolder(uri = profileState.profile[0].imageUri?.toUri(), size = Size.Lg )
                    Text("Name: MarioIlGrosso",
                        modifier = Modifier.padding(top = 20.dp))
                }
                Divider(modifier = Modifier.padding(5.dp))
            }
        }
    )
}