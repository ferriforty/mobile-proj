package com.example.mobile_proj.ui.screens.favoriteWorkout

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_proj.database.Connection
import com.example.mobile_proj.ui.Route
import com.example.mobile_proj.ui.WorkoutState
import com.example.mobile_proj.ui.WorkoutViewModel
import com.example.mobile_proj.ui.composables.TopAppBar
import com.example.mobile_proj.ui.screens.home.WorkoutRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    workoutViewModel: WorkoutViewModel,
    state: WorkoutState, navController:
    NavHostController,
    db: Connection,
    context: Context
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar ={ TopAppBar(navController, Route.FavoriteWorkout, scrollBehavior) },
    ) { contentPadding ->
        if(state.workout.isNotEmpty()) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(contentPadding)
            ) {
                items(state.workout) { item ->
                    WorkoutRow(workoutViewModel, item = item, db, context)
                }
            }
        }else {
            NoItemsFavorite(modifier = Modifier
                .padding(contentPadding)
            )
        }
    }
}

@Composable
fun NoItemsFavorite(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Icon(
            Icons.Outlined.Warning, "Warning",
            modifier = Modifier
                .padding(bottom = 16.dp)
                .size(48.dp),
            tint = MaterialTheme.colorScheme.secondary
        )
        Text(
            "No Favorite Workout",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row {
            Text("Tap the", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.padding(end = 8.dp))
            Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorite")
            Spacer(modifier = Modifier.padding(end = 8.dp))
            Text("button to add favorite Workout", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
