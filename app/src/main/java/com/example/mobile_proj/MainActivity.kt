package com.example.mobile_proj

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobile_proj.ui.theme.MobileprojTheme
import org.json.JSONArray
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getComments()
        setContent {
            MobileprojTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
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