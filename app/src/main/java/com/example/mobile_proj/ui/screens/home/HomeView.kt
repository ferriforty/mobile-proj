package com.example.mobile_proj.ui.screens.home

import android.content.Context
import android.content.Intent
import android.provider.Settings
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
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_proj.data.database.Workout
import com.example.mobile_proj.database.AlertDialogConnection
import com.example.mobile_proj.database.Connection
import com.example.mobile_proj.ui.Route
import com.example.mobile_proj.ui.WorkoutState
import com.example.mobile_proj.ui.WorkoutViewModel
import com.example.mobile_proj.ui.composables.BottomAppBar
import com.example.mobile_proj.ui.composables.TopAppBar
import io.realm.kotlin.mongodb.exceptions.InvalidCredentialsException
import io.realm.kotlin.mongodb.exceptions.ServiceException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    workoutViewModel: WorkoutViewModel,
    state: WorkoutState,
    navController: NavHostController,
    db: Connection,
    context: Context
) {
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
                    WorkoutRow(workoutViewModel, item = item, db, context)
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
fun WorkoutRow(workoutViewModel: WorkoutViewModel, item: Workout, db: Connection, context: Context) {
    val openAlertDialog = remember { mutableStateOf(false) }
    val openAlertDialogCreds = remember { mutableStateOf(false) }
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
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(fontWeight = FontWeight.ExtraBold,
                    text = item.muscleGroup,
                    modifier = Modifier.padding(start = 12.dp))
                Text(text = item.exercise,
                    modifier = Modifier.padding(start = 12.dp))
            }
            Column {
                Row {
                    var isFavorite by remember { mutableStateOf(item.favorite) }
                    IconToggleButton(
                        checked = isFavorite,
                        onCheckedChange = {
                            isFavorite = !isFavorite
                            workoutViewModel.setFavorite(item.id, isFavorite)
                        }
                    ) {
                        Icon(
                            imageVector = if (isFavorite) {
                                Icons.Filled.Favorite
                            } else {
                                Icons.Default.FavoriteBorder
                            },
                            contentDescription = null
                        )
                    }
                    Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        onClick = {
                            try {
                                db.deleteWorkout(item.idRemote)
                            } catch (e: ServiceException) {
                                openAlertDialog.value = true
                            } catch (e: InvalidCredentialsException) {
                                openAlertDialogCreds.value = true
                            }
                            workoutViewModel.deleteWorkout(item)
                        }
                    ) {
                        Icon(Icons.Outlined.Delete, "Delete Workout", tint = Color.White)
                    }
                }
            }
        }
    }
    when {
        openAlertDialog.value -> {
            AlertDialogConnection(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = { openAlertDialog.value = false },
                onDismissButton = { context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))},
                dialogTitle = "Error in connecting to database",
                dialogText = "Check your wi-fi connection",
                icon = Icons.Default.Error
            )
        }
        openAlertDialogCreds.value -> {
            AlertDialogConnection(
                onDismissRequest = {},
                onConfirmation = {},
                onDismissButton = {},
                dialogTitle = "Wrong Credentials used to log in to server",
                dialogText = "Report the bug and a patch will soon arrive",
                icon = Icons.Default.BugReport
            )
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
