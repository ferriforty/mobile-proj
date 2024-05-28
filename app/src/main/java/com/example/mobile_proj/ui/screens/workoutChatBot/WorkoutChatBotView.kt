package com.example.mobile_proj.ui.screens.workoutChatBot

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.volley.VolleyError
import com.example.mobile_proj.ui.Route
import com.example.mobile_proj.ui.composables.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBotScreen(navController: NavHostController,
                  workoutChatBotViewModel: WorkoutChatBotViewModel,
                  data: String) {
    val ctx = LocalContext.current
    var stringOutput by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(navController, Route.ChatBot, null)
        Text("Chest")
        Text(data)
        Button(onClick = { workoutChatBotViewModel.buttonLlamaAPI(data, ctx, object : ApiCallback {
            override fun onSuccess(result: String) {
              stringOutput = result
            }

            override fun onError(error: VolleyError) {
                stringOutput = error.toString()
            }
        })}) {
            Text("bot in azione!")
        }
        Text(stringOutput)
    }
}