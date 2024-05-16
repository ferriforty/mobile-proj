package com.example.mobile_proj

import android.os.Bundle
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
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.ext.call
import kotlinx.coroutines.runBlocking
import org.mongodb.kbson.BsonArray


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
                    Greeting("ciao")
                }
            }
        }
    }
}

fun getComments() {

    val app = App.create(BuildConfig.APP_ID) // Replace this with your App ID
    runBlocking {
        // Log in the user with the credentials associated
        // with the authentication provider
        // If successful, returns an authenticated `User` object
        val credentials = Credentials.emailPassword(BuildConfig.EMAIL, BuildConfig.PASSWORD);
        val user = app.login(credentials)
        // ... work with the user ...
        println(user)

        val c = user.functions.call<BsonArray>("getUsers", "")
        for (x in c) {
            println(x)
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