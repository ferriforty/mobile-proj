package com.example.mobile_proj.ui.screens.schedule

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsProperties.ToggleableState
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.example.gps.utils.StartMonitoringResult
import com.example.mobile_proj.database.Connection
import com.example.mobile_proj.ui.Route
import com.example.mobile_proj.ui.composables.ProfileImageHolder
import com.example.mobile_proj.ui.composables.Size
import com.example.mobile_proj.ui.composables.TopAppBar
import com.example.mobile_proj.ui.screens.map.OsmdroidMapView
import com.example.mobile_proj.ui.screens.profile.ProfileState
import com.example.mobile_proj.ui.screens.settings.SettingsRow
import com.example.mobile_proj.ui.screens.settings.ThemeChangeRow
import com.example.mobile_proj.ui.screens.settings.ThemeState
import com.example.mobile_proj.ui.screens.settings.ThemeViewModel
import com.example.mobile_proj.utils.PermissionStatus
import com.example.mobile_proj.utils.rememberPermission

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleView(
    navController: NavHostController,
    db: Connection,
    context: Context
) {

    var showPermissionAlert by remember { mutableStateOf(false) }
    val notificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermission(
            Manifest.permission.POST_NOTIFICATIONS
        ) { status ->
            when (status) {
                PermissionStatus.Granted -> {}
                PermissionStatus.Denied -> {}
                PermissionStatus.PermanentlyDenied -> { showPermissionAlert = true }
                PermissionStatus.Unknown -> {}
            }
        }
    } else {
        Toast.makeText(context, "Android version low, update your phone", Toast.LENGTH_LONG).show()
        return
    }

    // Initialize states for the child checkboxes
    val childCheckedStates = remember { mutableStateListOf(false, false, false, false, false, false, false) }

    // Compute the parent state based on children's states
    val parentState = when {
        childCheckedStates.all { it } -> androidx.compose.ui.state.ToggleableState.On
        childCheckedStates.none { it } -> androidx.compose.ui.state.ToggleableState.Off
        else -> androidx.compose.ui.state.ToggleableState.Indeterminate
    }

    Column {
        TopAppBar(navController, Route.Schedule, null)

        Spacer(modifier = Modifier.height(12.dp))
        // Parent TriStateCheckbox
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Select all")
            TriStateCheckbox(
                state = parentState,
                onClick = {
                    // Determine new state based on current state
                    val newState = parentState != androidx.compose.ui.state.ToggleableState.On
                    childCheckedStates.forEachIndexed { index, _ ->
                        childCheckedStates[index] = newState
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        val weekDays = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

        // Child Checkboxes
        childCheckedStates.forEachIndexed { index, checked ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(weekDays[index])
                Checkbox(
                    checked = checked,
                    onCheckedChange = { isChecked ->
                        // Update the individual child state
                        childCheckedStates[index] = isChecked
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(18.dp))
        if (childCheckedStates.all { it }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("All options selected")
            }
        }
        
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                val scheduled = ScheduleViewModel(context)
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    notificationPermission.launchPermissionRequest()
                }

                val weeksArray = weekDays.filterIndexed { index, _ ->  childCheckedStates[index]}.toTypedArray()
                scheduled.schedulePushNotifications(weeksArray)
                Toast.makeText(context, "Workout Schedule Updated", Toast.LENGTH_SHORT).show()
                db.insertWorkoutSchedSharedPreference(weeksArray)
                navController.navigateUp()

            }) {
                Text(text = "Submit Schedule")
                
            }
        }
    }

    if (showPermissionAlert) {
        AlertDialog(
            title = { Text("Notification Permission not granted.") },
            text = { Text("Go to Settings") },
            confirmButton = {
                TextButton(onClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", context.packageName, null)
                    intent.setData(uri)
                    context.startActivity(intent)
                    showPermissionAlert = false
                }) {
                    Text("Go to App Settings")
                }
            },
            onDismissRequest = { showPermissionAlert = false }
        )
    }
}



