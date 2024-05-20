package com.example.mobile_proj.ui.screens.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun SignUp(navController: NavHostController, db: Connection) {
    Surface {
        var credentials by remember { mutableStateOf(CredentialsSignUp()) }
        val context = LocalContext.current

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            LoginField(
                value = credentials.name,
                onChange = { data -> credentials = credentials.copy(name = data) },
                modifier = Modifier.fillMaxWidth()
            )
            LoginField(
                value = credentials.surname,
                onChange = { data -> credentials = credentials.copy(surname = data) },
                modifier = Modifier.fillMaxWidth()
            )
            LoginField(
                value = credentials.username,
                onChange = { data -> credentials = credentials.copy(username = data) },
                modifier = Modifier.fillMaxWidth()
            )
            PasswordField(
                value = credentials.pwd,
                onChange = { data -> credentials = credentials.copy(pwd = data) },
                submit = {
                    if (!checkCredentials(credentials, context, db)) credentials = CredentialsSignUp()
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
                    if (!checkCredentials(credentials, context, db)) credentials = CredentialsSignUp()
                },
                enabled = credentials.isNotEmpty(),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
            Spacer(modifier = Modifier.height(30.dp))
            TextButton(
                onClick = {
                    navController.navigate(RouteAuth.SignInRoute.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Already have an account? Sign In")
            }
        }
    }
}

data class CredentialsSignUp(
    var name: String = "",
    var surname: String = "",
    var username: String = "",
    var pwd: String = "",
    var date: String = "",
    var profileImage: ByteArray = ByteArray(0),
    var remember: Boolean = false
) {
    fun isNotEmpty(): Boolean {
        return username.isNotEmpty() && pwd.isNotEmpty()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CredentialsSignUp

        return profileImage.contentEquals(other.profileImage)
    }

    override fun hashCode(): Int {
        return profileImage.contentHashCode()
    }
}

private fun checkCredentials(creds: CredentialsSignUp, context: Context, db: Connection): Boolean {
    if (creds.isNotEmpty() && signUpCheck(creds, db)) {
        context.startActivity(Intent(context, MainActivity::class.java))
        (context as Activity).finish()
        return true
    }
    Toast.makeText(context, "Wrong Credentials", Toast.LENGTH_SHORT).show()
    return false
}