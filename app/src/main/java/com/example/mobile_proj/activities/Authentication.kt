package com.example.mobile_proj.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.mobile_proj.MainActivity
import com.example.mobile_proj.data.models.Theme
import com.example.mobile_proj.database.AlertDialogConnection
import com.example.mobile_proj.database.Connection
import com.example.mobile_proj.ui.NavGraphAuth
import com.example.mobile_proj.ui.RouteAuth
import com.example.mobile_proj.ui.screens.auth.signInCheckWithToken
import com.example.mobile_proj.ui.screens.settings.ThemeViewModel
import com.example.mobile_proj.ui.theme.MobileprojTheme
import io.realm.kotlin.mongodb.exceptions.InvalidCredentialsException
import io.realm.kotlin.mongodb.exceptions.ServiceException
import org.koin.androidx.compose.koinViewModel

class Authentication : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Connection(this)
        val intent = Intent(this, MainActivity::class.java)
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
                    val openAlertDialog = remember { mutableStateOf(false) }
                    val openAlertDialogCreds = remember { mutableStateOf(false) }

                    val navController = rememberNavController()

                    Scaffold { contentPadding ->
                        NavGraphAuth(navController, modifier = Modifier.padding(contentPadding), db, intent)
                    }


                    try {
                        db.start()
                    } catch (e: ServiceException) {
                        openAlertDialog.value = true
                    } catch (e: InvalidCredentialsException) {
                        openAlertDialogCreds.value = true
                    }

                    when {
                        openAlertDialog.value -> {
                            AlertDialogConnection(
                                onDismissRequest = { openAlertDialog.value = false },
                                onConfirmation = { openAlertDialog.value = false },
                                onDismissButton = { this.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))},
                                dialogTitle = "Error in connecting to database",
                                dialogText = "Check your wi-fi connection",
                                icon = Icons.Default.Error
                            )
                        }
                        openAlertDialogCreds.value -> {
                            AlertDialogConnection(
                                onDismissRequest = {},
                                onConfirmation = {},
                                onDismissButton = {},
                                dialogTitle = "Wrong Credentials used to log in to server",
                                dialogText = "Report the bug and a patch will soon arrive",
                                icon = Icons.Default.BugReport
                            )
                        }
                    }
                    val autoLogCred = db.retrieveFromSharedPreference()

                    if (autoLogCred.first.isNotEmpty() && autoLogCred.second.isNotEmpty()) {

                        try {
                            if (signInCheckWithToken(autoLogCred.first, autoLogCred.second, db)) {
                                intent.putExtra("reload", false)
                                this.startActivity(intent)
                                (this as Activity).finish()
                            }
                        } catch (e: ServiceException) {
                            openAlertDialog.value = true
                        } catch (e: InvalidCredentialsException) {
                            openAlertDialogCreds.value = true
                        }
                    }
                    RouteAuth.SignInRoute
                }
            }
        }
    }
}
