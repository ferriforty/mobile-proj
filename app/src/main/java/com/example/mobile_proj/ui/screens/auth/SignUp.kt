package com.example.mobile_proj.ui.screens.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.text.format.DateFormat
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdsClick
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobile_proj.MainActivity
import com.example.mobile_proj.database.AlertDialogConnection
import com.example.mobile_proj.database.Connection
import com.example.mobile_proj.ui.RouteAuth
import io.realm.kotlin.mongodb.exceptions.InvalidCredentialsException
import io.realm.kotlin.mongodb.exceptions.ServiceException
import kotlinx.serialization.Serializable
import java.math.BigInteger
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(navController: NavHostController, db: Connection) {

    val date = remember {
        Calendar.getInstance().apply {
            set(Calendar.YEAR, 2023)
            set(Calendar.MONTH, 7)
            set(Calendar.DAY_OF_MONTH, 23)
        }.timeInMillis
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = date,
        yearRange = 1990..2024
    )
    var showDatePicker by remember { mutableStateOf(false) }

    var credentials by remember { mutableStateOf(CredentialsSignUp()) }
    val context = LocalContext.current

    val openAlertDialog = remember { mutableStateOf(false) }
    val openAlertDialogCreds = remember { mutableStateOf(false) }

    when {
        openAlertDialog.value -> {
            AlertDialogConnection(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = { openAlertDialog.value = false },
                onDismissButton = { context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))},
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

    Surface {

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
                modifier = Modifier.fillMaxWidth(),
                label = "Name",
                placeholder = "Enter your name",
                icon = Icons.Default.PersonOutline
            )
            LoginField(
                value = credentials.surname,
                onChange = { data -> credentials = credentials.copy(surname = data) },
                modifier = Modifier.fillMaxWidth(),
                label = "Surname",
                placeholder = "Enter your Surname",
                icon = Icons.Default.PersonOutline
            )
            LoginField(
                value = credentials.username,
                onChange = { data -> credentials = credentials.copy(username = data) },
                modifier = Modifier.fillMaxWidth(),
                label = "Username",
                placeholder = "Enter your username"
            )
            PasswordField(
                value = credentials.password,
                onChange = { data -> credentials = credentials.copy(password = data) },
                submit = {
                    try {
                        if (!checkCredentials(credentials, context, db)) credentials = CredentialsSignUp()
                    } catch (e: ServiceException) {
                        openAlertDialog.value = true
                    } catch (e: InvalidCredentialsException) {
                        openAlertDialogCreds.value = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = if (credentials.birthDate == 0L) {"No Date Selected"} else {
                    DateFormat.format("E, dd MMM yyyy", Date(credentials.birthDate)).toString()
                },
                modifier = Modifier.padding(bottom = 8.dp))
            FilledTonalButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        Icons.Default.CalendarMonth,
                        contentDescription = "calendar",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                    Text( text = "Date Picker" )
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "click",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }

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
                    try {
                        if (!checkCredentials(credentials, context, db)) credentials = CredentialsSignUp()
                    } catch (e: ServiceException) {
                        openAlertDialog.value = true
                    } catch (e: InvalidCredentialsException) {
                        openAlertDialogCreds.value = true
                    }
                },
                enabled = credentials.isNotEmpty(),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign Up")
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

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {},
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedDate = Calendar.getInstance().apply {
                            timeInMillis = datePickerState.selectedDateMillis!!
                        }
                        if (!selectedDate.after(Calendar.getInstance())) {
                            credentials.birthDate = selectedDate.timeInMillis
                            showDatePicker = false
                        } else {
                            Toast.makeText(
                                context,
                                "Selected date should be after today, please select again",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) { Text("Cancel") }
            }
        )
        {
            DatePicker(state = datePickerState)
        }
    }
}

data class CredentialsSignUp(
    var name: String = "",
    var surname: String = "",
    var username: String = "",
    var password: String = "",
    var birthDate: Long = 0,
    var remember: Boolean = false
) {
    fun isNotEmpty(): Boolean {
        return username.isNotEmpty() &&
                password.isNotEmpty() &&
                name.isNotEmpty() &&
                surname.isNotEmpty() &&
                birthDate != 0L
    }
}

private fun checkCredentials(creds: CredentialsSignUp, context: Context, db: Connection): Boolean {

    if (!creds.isNotEmpty()) {
        Toast.makeText(context, "Wrong Credentials", Toast.LENGTH_SHORT).show()
        return false
    }

    if (userExists(creds.username, db)) {
        Toast.makeText(context, "Username already used", Toast.LENGTH_SHORT).show()
        return false
    }

    if (!signUpCheck(creds, db)) {
        Toast.makeText(context, "Unable to create User", Toast.LENGTH_SHORT).show()
        return false
    }

    context.startActivity(Intent(context, MainActivity::class.java))
    (context as Activity).finish()
    return true
}
