package com.example.mobile_proj.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_proj.data.database.Workout
import com.example.mobile_proj.ui.Route
import com.example.mobile_proj.ui.WorkoutState
import com.example.mobile_proj.ui.composables.BottomAppBar
import com.example.mobile_proj.ui.composables.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(state: WorkoutState, navController: NavHostController) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar ={ TopAppBar(navController, Route.Home, scrollBehavior) },
        floatingActionButton = {
            Box {
                FloatingActionButton(
                    onClick = { navController.navigate(Route.AddWorkout.route)},
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(60.dp)
                        .offset(y = 50.dp)
                ) {
                    Icon(Icons.Outlined.Add, "Add Workout")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
                BottomAppBar(navController)
        }
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
                    WorkoutRow(item = item)
                }
            }
        }else {
            NoItemsPlaceholder(modifier = Modifier
                .padding(contentPadding)
            )
        }
    }
}

@Composable
fun WorkoutRow(item: Workout) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = BorderStroke(2.dp, Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize().padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(fontWeight = FontWeight.ExtraBold,
                    text = item.muscleGroup,
                    modifier = Modifier.padding(start = 12.dp))
                Text(text = item.exercise,
                    modifier = Modifier.padding(start = 12.dp))
            }
            Icon(Icons.Outlined.Delete, "Delete Workout")
        }
    }
}

@Composable
fun NoItemsPlaceholder(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(
            Modifier
                .padding(4.dp)
                .height(70.dp)
        )
        Icon(
            Icons.Outlined.Warning, "Warning",
            modifier = Modifier
                .padding(bottom = 16.dp)
                .size(48.dp),
            tint = MaterialTheme.colorScheme.secondary
        )
        Text(
            "No items",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            "Tap the + button to add a new workout.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
