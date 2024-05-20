package com.example.mobile_proj.ui.screens.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_proj.ui.Route
import com.example.mobile_proj.ui.composables.TopAppBar


@Composable
fun ProfileScreen(navController: NavHostController) {
    Scaffold (
        topBar = { TopAppBar(navController, Route.Profile) },
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                horizontalAlignment = Alignment.Start
            ) {
                Row {
                    Icon(Icons.Outlined.AccountCircle,
                        "profile-image",
                        modifier = Modifier.size(156.dp))
                    Text("Name: MarioIlGrosso",
                        modifier = Modifier.padding(top = 20.dp))
                }
                Divider(modifier = Modifier.padding(5.dp))
            }
        }
    )
}