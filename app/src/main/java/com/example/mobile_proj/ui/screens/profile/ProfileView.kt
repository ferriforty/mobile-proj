package com.example.mobile_proj.ui.screens.profile

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobile_proj.database.Connection
import com.example.mobile_proj.ui.Route
import com.example.mobile_proj.ui.composables.ProfileImageHolder
import com.example.mobile_proj.ui.composables.Size
import com.example.mobile_proj.ui.composables.TopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(state: ProfileState,
                  navController: NavHostController, db: Connection
) {

    val schedule = db.retrieveWorkoutSchedSharedPreference().ifEmpty { "No Workout Schedule Present" }

    Scaffold (
        topBar = { TopAppBar(navController, Route.Profile, null) },
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                horizontalAlignment = Alignment.Start
            ) {
                if (state.profile.isNotEmpty()) {
                    Spacer(modifier = Modifier.padding(12.dp))
                    Row {
                        val imageUri = Uri.parse(state.profile[0].imageUri)
                        ProfileImageHolder(imageUri, Size.Lg)
                        Text("Username: " +state.profile[0].username,
                            modifier = Modifier.padding(20.dp))
                    }
                    Divider(modifier = Modifier.padding(10.dp))
                    Spacer(modifier = Modifier.padding(18.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Workout Schedule", fontSize = 20.sp, fontWeight = FontWeight.W700)
                    }
                    Spacer(modifier = Modifier.padding(9.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = schedule)
                    }
                }
            }
        }
    )
}