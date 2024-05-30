package com.example.mobile_proj.ui.screens.workoutDetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_proj.data.database.Workout
import com.example.mobile_proj.ui.Route
import com.example.mobile_proj.ui.composables.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(navController: NavHostController, workout: Workout) {
    Scaffold (
        topBar = { TopAppBar(navController, Route.WorkoutDetails, null) }
    ) { contentPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.size(16.dp))
            Card(
                border = BorderStroke(2.dp, Color.White),
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.ExtraBold,
                        text = workout.muscleGroup,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                border = BorderStroke(2.dp, Color.White),
                modifier = Modifier
                    .padding(4.dp)
                    .height(650.dp)
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = workout.exercise,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Divider(
                        color = Color.White,
                        modifier = Modifier.padding(2.dp)
                    )
                    Spacer(Modifier.size(8.dp))
                    if (workout.botchat.isNotEmpty()) {
                        Text(
                            modifier = Modifier.padding(6.dp),
                            text = workout.botchat,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    } else {
                        Text(
                            modifier = Modifier.padding(6.dp),
                            text = "Try to ask the Bot for help.",
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}