package com.example.mobile_proj.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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

class Authentication : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Connection(this);

        val autoLogCred = db.retrieveFromSharedPreference()

        if (autoLogCred.first.isNotEmpty() && autoLogCred.second.isNotEmpty()) {
            if (signInCheckWithToken(autoLogCred.first, autoLogCred.second, db)) {
                this.startActivity(Intent(this, MainActivity::class.java))
                (this as Activity).finish()
            }
        }

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
                    val navController = rememberNavController()

                    Scaffold { contentPadding ->
                        NavGraphAuth(navController, modifier = Modifier.padding(contentPadding), db)
                    }
                    RouteAuth.SignInRoute
                }
            }
        }
    }
}
