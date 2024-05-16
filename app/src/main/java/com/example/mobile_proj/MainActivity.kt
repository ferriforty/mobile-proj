package com.example.mobile_proj

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobile_proj.ui.NavGraph
import com.example.mobile_proj.ui.Route
import com.example.mobile_proj.ui.composables.BottomAppBar
import com.example.mobile_proj.ui.composables.TopAppBar
import com.example.mobile_proj.ui.theme.MobileprojTheme
import org.json.JSONArray
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //getComments()
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
                        bottomBar = { BottomAppBar(navController)}
                    ) { contentPadding ->
                        NavGraph(navController, modifier = Modifier.padding(contentPadding))
                    }

                }
            }
        }
    }
}

fun getComments() {
    val mongodbURL = "https://eu-central-1.aws.data.mongodb-api.com/app/application-0-wqzctuv/endpoint/getComments";
    /*val url = URL(mongodbURL)
    val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
    val inputStreamReader = InputStreamReader(urlConnection.inputStream)
    val bufferedReader = BufferedReader(inputStreamReader)

    val stringBuilder = StringBuilder()
    var line:String?
    while (bufferedReader.readLine().also { line = it } != null) {
        stringBuilder.append(line)
    }

    val jsonArray = JSONArray(stringBuilder.toString())

    println(jsonArray)*/
    val url = URL(mongodbURL)
    val executor = Executors.newSingleThreadExecutor()
    executor.execute {
        val stringBuilder = StringBuilder()
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"  // optional default is GET

            println("\nSent 'GET' request to URL : $url; Response Code : $responseCode")
            inputStream.bufferedReader().use {
                it.lines().forEach { line ->
                    println(line)
                    stringBuilder.append(line)
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