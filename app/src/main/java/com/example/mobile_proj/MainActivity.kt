package com.example.mobile_proj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobile_proj.ui.NavGraph
import com.example.mobile_proj.ui.Route
import com.example.mobile_proj.ui.composables.BottomAppBar
import com.example.mobile_proj.ui.composables.TopAppBar
import com.example.mobile_proj.ui.theme.MobileprojTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileprojTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val backStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute by remember {
                        derivedStateOf {
                            Route.routes.find {
                                it.route == backStackEntry?.destination?.route
                            } ?: Route.Home
                        }
                    }
                    Scaffold(
                        topBar = { TopAppBar(navController, currentRoute) },
                        bottomBar = {
                            if(currentRoute == Route.Home) {
                                BottomAppBar(navController)}
                            }
                    ) { contentPadding ->
                        NavGraph(navController, modifier = Modifier.padding(contentPadding))
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MobileprojTheme {
        Greeting("Android")
    }
}