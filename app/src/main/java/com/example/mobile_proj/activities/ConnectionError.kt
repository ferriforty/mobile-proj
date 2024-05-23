package com.example.mobile_proj.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.mobile_proj.MainActivity
import com.example.mobile_proj.data.models.Theme
import com.example.mobile_proj.database.Connection
import com.example.mobile_proj.ui.NavGraphAuth
import com.example.mobile_proj.ui.RouteAuth
import com.example.mobile_proj.ui.screens.auth.signInCheckWithToken
import com.example.mobile_proj.ui.screens.settings.ThemeViewModel
import com.example.mobile_proj.ui.theme.MobileprojTheme
import org.koin.androidx.compose.koinViewModel

class ConnectionError : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val themeViewModel = koinViewModel<ThemeViewModel>()
            val themeState by themeViewModel.state.collectAsStateWithLifecycle()
            MobileprojTheme(darkTheme = when (themeState.theme) {
                Theme.Light -> false
                Theme.Dark -> true
                Theme.System -> isSystemInDarkTheme()
            }){

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Icon( imageVector = Icons.Default.Error, contentDescription = "Error icon" )
                        Text(
                            text = "Connection error",
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer( modifier = Modifier.height(16.dp) )
                        Text(
                            text = "Could not connect to Database",
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "Check your wi-fi connection",
                            modifier = Modifier.fillMaxWidth()
                        )
                        Button(
                            onClick = {
                                this@ConnectionError.startActivity(Intent(this@ConnectionError, Authentication::class.java))
                                (this as Activity).finish() },
                            shape = RoundedCornerShape(5.dp),
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Text( text = "Try Again" )
                        }
                        Button( onClick = {
                            /*TODO*/ },
                            shape = RoundedCornerShape(5.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text( text = "Go to Settings" )
                        }
                    }
                }
            }
        }
    }
}
