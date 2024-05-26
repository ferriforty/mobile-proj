package com.example.mobile_proj.ui.screens.addWorkout

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_proj.R
import com.example.mobile_proj.ui.Route
import com.example.mobile_proj.ui.composables.TopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkoutScreen(
    navController: NavHostController
) {
    Scaffold (
        topBar = { TopAppBar(navController, Route.AddWorkout, null) }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current
            val muscleGroupList = stringArrayResource(R.array.muscular_group)

            var expanded by remember { mutableStateOf(false) }
            var selectedText by remember { mutableStateOf(muscleGroupList[0]) }


            Text(text = "Pick a muscle group",
                modifier = Modifier.padding(contentPadding))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 48.dp, top = 12.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    TextField(
                        value = selectedText,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        muscleGroupList.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    selectedText = item
                                    expanded = false
                                    Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    }
                }
            }
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Text("Choose Exercises",
                    modifier = Modifier.padding(contentPadding)
                )
                Divider(modifier = Modifier.padding(10.dp))
                Column(Modifier.selectableGroup()) {
                    Row(
                        Modifier
                            .height(56.dp)
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        when (selectedText) {
                            muscleGroupList[0] -> Icon(painter = painterResource(id = R.drawable.ic_light_theme),
                                contentDescription = "light-theme-icon",
                                modifier = Modifier.size(30.dp))
                            "Back" -> Icon(painter = painterResource(id = R.drawable.ic_dark_theme),
                                contentDescription = "dark-theme-icon",
                                modifier = Modifier.size(30.dp))
                            else -> {
                                Icon(painter = painterResource(id = R.drawable.ic_system_theme),
                                    contentDescription = "dark-theme-icon",
                                    modifier = Modifier.size(30.dp))
                            }
                        }
                    }
                    Divider(modifier = Modifier.padding(5.dp))
                }
            }
        }
    }
}