package com.example.mobile_proj.ui.screens.addWorkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
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
            val muscleGroupList = stringArrayResource(R.array.muscular_group)
            val chestExercises = stringArrayResource(R.array.chest_exercises)
            val backExercises = stringArrayResource(R.array.back_exercises)
            val shoulderExercises = stringArrayResource(R.array.shoulder_exercises)
            val tricepsExercises = stringArrayResource(R.array.triceps_exercises)
            val bicepsExercises = stringArrayResource(R.array.biceps_exercises)
            val quadricepsExercises = stringArrayResource(R.array.quadriceps_exercises)
            val calvesExercises = stringArrayResource(R.array.calves_exercises)
            val forearmExercises = stringArrayResource(R.array.forearm_exercises)
            val coreExercises = stringArrayResource(R.array.core_exercises)
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
                                }
                            )
                        }
                    }
                }
            }
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text("Choose Exercises", modifier = Modifier.padding(top = 24.dp))
                Divider(modifier = Modifier.padding(10.dp))
                Column( modifier = Modifier
                    .selectableGroup()
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally)
                {
                    when (selectedText) {
                        muscleGroupList[0] -> {
                            var selectedOption by remember { mutableStateOf(chestExercises[0]) }
                            chestExercises.forEach { exercise ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(exercise)
                                    Spacer(modifier = Modifier.weight(1f))
                                    RadioButton(
                                        selected = exercise == selectedOption,
                                        onClick = { selectedOption = exercise}
                                    )
                                }
                            }
                            ButtonDetails(selectedText, selectedOption, navController)
                        }
                        muscleGroupList[1] -> {
                            var selectedOption by remember { mutableStateOf(backExercises[0]) }
                            backExercises.forEach { exercise ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(exercise)
                                    Spacer(modifier = Modifier.weight(1f))
                                    RadioButton(
                                        selected = exercise == selectedOption,
                                        onClick = { selectedOption = exercise}
                                    )
                                }
                            }
                            ButtonDetails(selectedText, selectedOption, navController)
                        }
                        muscleGroupList[2] -> {
                            var selectedOption by remember { mutableStateOf(shoulderExercises[0]) }
                            shoulderExercises.forEach { exercise ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(exercise)
                                    Spacer(modifier = Modifier.weight(1f))
                                    RadioButton(
                                        selected = exercise == selectedOption,
                                        onClick = { selectedOption = exercise}
                                    )
                                }
                            }
                            ButtonDetails(selectedText, selectedOption, navController)
                        }
                        muscleGroupList[3] -> {
                            var selectedOption by remember { mutableStateOf(tricepsExercises[0]) }
                            tricepsExercises.forEach { exercise ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(exercise)
                                    Spacer(modifier = Modifier.weight(1f))
                                    RadioButton(
                                        selected = exercise == selectedOption,
                                        onClick = { selectedOption = exercise}
                                    )
                                }
                            }
                            ButtonDetails(selectedText, selectedOption, navController)
                        }
                        muscleGroupList[4] -> {
                            var selectedOption by remember { mutableStateOf(bicepsExercises[0]) }
                            bicepsExercises.forEach { exercise ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(exercise)
                                    Spacer(modifier = Modifier.weight(1f))
                                    RadioButton(
                                        selected = exercise == selectedOption,
                                        onClick = { selectedOption = exercise}
                                    )
                                }
                            }
                            ButtonDetails(selectedText, selectedOption, navController)
                        }
                        muscleGroupList[5] -> {
                            var selectedOption by remember { mutableStateOf(quadricepsExercises[0]) }
                            quadricepsExercises.forEach { exercise ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(exercise)
                                    Spacer(modifier = Modifier.weight(1f))
                                    RadioButton(
                                        selected = exercise == selectedOption,
                                        onClick = { selectedOption = exercise}
                                    )
                                }
                            }
                            ButtonDetails(selectedText, selectedOption, navController)
                        }
                        muscleGroupList[6] -> {
                            var selectedOption by remember { mutableStateOf(calvesExercises[0]) }
                            calvesExercises.forEach { exercise ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(exercise)
                                    Spacer(modifier = Modifier.weight(1f))
                                    RadioButton(
                                        selected = exercise == selectedOption,
                                        onClick = { selectedOption = exercise}
                                    )
                                }
                            }
                            ButtonDetails(selectedText, selectedOption, navController)
                        }
                        muscleGroupList[7] -> {
                            var selectedOption by remember { mutableStateOf(forearmExercises[0]) }
                            forearmExercises.forEach { exercise ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(exercise)
                                    Spacer(modifier = Modifier.weight(1f))
                                    RadioButton(
                                        selected = exercise == selectedOption,
                                        onClick = { selectedOption = exercise}
                                    )
                                }
                            }
                            ButtonDetails(selectedText, selectedOption, navController)
                        }
                        muscleGroupList[8] -> {
                            var selectedOption by remember { mutableStateOf(coreExercises[0]) }
                            coreExercises.forEach { exercise ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(exercise)
                                    Spacer(modifier = Modifier.weight(1f))
                                    RadioButton(
                                        selected = exercise == selectedOption,
                                        onClick = { selectedOption = exercise}
                                    )
                                }
                            }
                            ButtonDetails(selectedText, selectedOption, navController)
                        }
                        else -> {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_system_theme),
                                contentDescription = "dark-theme-icon",
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
                Divider(modifier = Modifier.padding(5.dp))
            }
        }
    }
}

@Composable
fun ButtonDetails(selectedMusculeGroup: String,selectedOption: String, navController: NavHostController) {
    Button(onClick = { navController.navigate(Route.ChatBot.buildRoute(selectedOption)) }) {
        Text("Details")
    }
}