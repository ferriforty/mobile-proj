package com.example.mobile_proj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobile_proj.data.models.Theme
import com.example.mobile_proj.ui.NavGraph
import com.example.mobile_proj.ui.Route
import com.example.mobile_proj.ui.screens.home.HomeScreen
import com.example.mobile_proj.ui.screens.settings.ThemeViewModel
import com.example.mobile_proj.ui.theme.MobileprojTheme
import org.koin.androidx.compose.koinViewModel


class MainActivity : ComponentActivity() {
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
                    val navController = rememberNavController()
                    val backStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute by remember {
                        derivedStateOf {
                            Route.routes.find {
                                it.route == backStackEntry?.destination?.route
                            } ?: Route.Home
                        }
                    }
                    Scaffold { contentPadding ->
                        NavGraph(navController, modifier = Modifier.padding(contentPadding), themeState, themeViewModel)
                    }
                    if(currentRoute == Route.Home) {
                        HomeScreen(navController)
                    }

                }
            }
        }
    }
}
