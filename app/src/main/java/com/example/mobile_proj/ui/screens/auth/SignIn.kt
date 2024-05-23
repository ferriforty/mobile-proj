package com.example.mobile_proj.ui.screens.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.textclassifier.TextLinks.TextLink
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.mobile_proj.MainActivity
import com.example.mobile_proj.database.Connection
import com.example.mobile_proj.ui.RouteAuth

@Composable
fun SignIn(navController: NavHostController, db: Connection) {

    Surface {
        var credentials by remember { mutableStateOf(CredentialsSignIn()) }
        val context = LocalContext.current

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            LoginField(
                value = credentials.username,
                onChange = { data -> credentials = credentials.copy(username = data) },
                modifier = Modifier.fillMaxWidth(),
                label = "Username",
                placeholder = "Enter your username",
            )
            PasswordField(
                value = credentials.pwd,
                onChange = { data -> credentials = credentials.copy(pwd = data) },
                submit = {
                    checkCredentials(credentials, context, db)
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            LabeledCheckbox(
                label = "Remember Me",
                onCheckChanged = {
                    credentials = credentials.copy(remember = !credentials.remember)
                },
                isChecked = credentials.remember
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    checkCredentials(credentials, context, db)
                },
                enabled = credentials.isNotEmpty(),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign In")
            }
            Spacer(modifier = Modifier.height(30.dp))
            TextButton(
                onClick = {
                    navController.navigate(RouteAuth.SignUpRoute.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("New user? Sign Up")
            }
        }
    }
}

data class CredentialsSignIn(
    var username: String = "",
    var pwd: String = "",
    var remember: Boolean = false
) {
    fun isNotEmpty(): Boolean {
        return username.isNotEmpty() && pwd.isNotEmpty()
    }
}

private fun checkCredentials(creds: CredentialsSignIn, context: Context, db: Connection): Boolean {
    if (creds.isNotEmpty() && signInCheck(creds, db)) {
        context.startActivity(Intent(context, MainActivity::class.java))
        (context as Activity).finish()
        return true
    }
    Toast.makeText(context, "Wrong Credentials", Toast.LENGTH_SHORT).show()
    return false
}
