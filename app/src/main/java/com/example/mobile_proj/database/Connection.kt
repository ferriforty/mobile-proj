package com.example.mobile_proj.database

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.mobile_proj.BuildConfig
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.User
import io.realm.kotlin.mongodb.ext.call
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.mobile_proj.data.database.Workout
import com.example.mobile_proj.ui.screens.addWorkout.AddWorkoutState
import com.google.gson.Gson
import org.mongodb.kbson.BsonDocument
import org.mongodb.kbson.BsonUndefined


/**
 * function to create instance of sharedPreferences
 *
 * @param context context of the main activity
 *
 */
private fun createSharedPreference(context: Context): SharedPreferences {

    val masterKeyAlias = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    return EncryptedSharedPreferences.create(
        context,
        "MyEncryptedAccessToken",
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}

/**
 * Connection object to manage the database
 */
class Connection(context: Context) {
    private val app: App = App.create(BuildConfig.APP_ID)
    private lateinit var user: User
    private var sharedPreferences: SharedPreferences
    private var context: Context

    init {
        this.context = context
        sharedPreferences = createSharedPreference(context)
    }

    /**
     * Function to start the connection, Must be called before any operation
     */
    fun start() {
        val credentials = Credentials.emailPassword(BuildConfig.EMAIL, BuildConfig.PASSWORD)
        runBlocking {
            user = app.login(credentials)
        }
    }

    /**
     * function to insert value in shared preference
     *
     * @param username username insert in the shared preference
     * @param accessToken username insert in the shared preference
     *
     */
    private fun insertSharedPreference(username: String, accessToken: String) {
        sharedPreferences.edit()
            .putString("username", username)
            .apply()

        sharedPreferences.edit()
            .putString("access_token", accessToken)
            .apply()
    }

    /**
     * function to insert workout schedule in shared preference
     *
     * @param weekArray array of days to insert in sharedPreferences
     *
     */
    fun insertWorkoutSchedSharedPreference(weekArray: Array<String>) {
        sharedPreferences.edit()
            .putString("weekArray", weekArray.joinToString())
            .apply()
    }

    /**
     * function to retrieve workout schedule from shared preference
     *
     * @return weekArray, array of weeks days for schedule workout
     *
     */
    fun retrieveWorkoutSchedSharedPreference(): String {
        return sharedPreferences.getString("weekArray", "").orEmpty()
    }

    /**
     * function to get username and access token from shared preference
     *
     * @return username as first value and token as second
     */
    fun retrieveFromSharedPreference(): Pair<String, String> {
        return Pair(
            sharedPreferences.getString("username", "").orEmpty(),
            sharedPreferences.getString("access_token","").orEmpty()
        )
    }

    /**
     * function to reset username and access token from shared preference
     */
    fun deleteSharedPreference() {

        sharedPreferences.edit()
            .putString("access_token", "")
            .apply()
    }

    /**
     * method to check if a user exists
     *
     * @param username email of the user
     *
     * @return true if it exists, false if not
     */
    fun userExists(username: String): Boolean {
        return runBlocking {
            return@runBlocking user.functions.call<Boolean>("user_exists", username)
        }
    }

    /**
     * sign in method
     *
     * @param username
     * @param password
     * @param keepSigned boolean flag that stores in sharedPreference a token if the user wants
     * to auto log in when opening the application
     * @return [res] the uuid or and empty String (if keepSigned is false) if everything went well
     * and error message if not
     */
    fun signIn(
        username: String,
        password: String,
        keepSigned: Boolean,
    ): JSONObject {
        val res = runBlocking {
            return@runBlocking JSONObject(
                user
                    .functions
                    .call<BsonDocument>("log_in", username, generateHash(password), keepSigned)
                    .toJson()
            )
        }

        if (JSONObject(res["code"].toString())["\$numberLong"] == "400" ||
            JSONObject(res["code"].toString())["\$numberLong"] == "404") {
            return res
        }
        res.put("same", retrieveFromSharedPreference().first == username)
        insertSharedPreference(username, res["access_token"].toString())
        return res
    }

    /**
     * automatic sign in with token
     *
     * @param username
     * @param authToken
     *
     * @return [res] code 404 if user not present in database,
     * 400 for error in retrieving data,
     * 200 if the user logged in correctly
     */
    fun signInToken( username: String, authToken: String ): JSONObject {

        val res = runBlocking {
            return@runBlocking JSONObject(
                user
                    .functions
                    .call<BsonDocument>("log_in_with_token", username, authToken)
                    .toJson()
            )
        }

        if (JSONObject(res["code"].toString())["\$numberLong"] == "400" ||
            JSONObject(res["code"].toString())["\$numberLong"] == "404") {
            return res
        }
        res.put("same", true)
        return res
    }

    /**
     * sign up method
     *
     * @param arg JSONObject =>
     *         "name": string,
     *         "surname": string,
     *         "username": string,
     *         "password": string,
     *         "birthDate": long,
     *         "remember": bool
     *
     * @return [res] the uuid or and empty String (if keepSigned is false) if everything went well
     * and error message if not
     */
    fun signUp(
        arg: JSONObject,
    ): JSONObject {

        arg.put("password", generateHash(arg.get("password").toString()))
        val res = runBlocking {
            return@runBlocking JSONObject(
                user
                    .functions
                    .call<BsonDocument>("insert_user_gymShred", arg.toString())
                    .toJson()
            )
        }

        if (JSONObject(res["code"].toString())["\$numberLong"] == "400") {
            return res
        }
        res.put("same", false)
        insertSharedPreference(arg["username"].toString(), res["access_token"].toString())
        return res
    }

    /**
     * insert workout
     *
     * @param workout
     */
    fun insertWorkout(
        workout: Workout
    ): String {
        val res = runBlocking {
            return@runBlocking JSONObject(user
                .functions
                .call<BsonDocument>("insert_workout", Gson().toJson(workout))
                .toJson()
            )
        }
        val remoteId = JSONObject(JSONObject(res["message"].toString())["insertedId"].toString())["\$oid"]
        return remoteId.toString()
    }

    /**
     * delete workout
     *
     * @param idRemote id of element to delete
     */
    fun deleteWorkout(
        idRemote: String
    ) {
        runBlocking {
            user
                .functions
                .call<BsonUndefined>("delete_workout", idRemote)
        }
    }

    /**
     * retrieve workouts
     *
     * @param username Username owner workouts
     */
    fun retrieveWorkouts(
        username: String
    ): Array<Workout>{
        val res = runBlocking {
            return@runBlocking JSONObject(
                user
                    .functions
                    .call<BsonDocument>("retrieve_workouts_by_user", username)
                    .toJson()
            )
        }

        if (JSONObject(res["code"].toString())["\$numberLong"] == "400") {
            return arrayOf()
        }

        val workoutJsonArray = res.getJSONArray("message")
        var workoutArray: Array<Workout> = arrayOf()

        for (i in 0..<workoutJsonArray.length()) {
            val workoutJson = JSONObject(workoutJsonArray[i].toString())

            workoutArray += Workout(
                username = workoutJson["username"].toString(),
                botchat =  workoutJson["botchat"].toString(),
                exercise =  workoutJson["exercise"].toString(),
                muscleGroup =  workoutJson["muscleGroup"].toString(),
                favorite =  false,
                idRemote = JSONObject(workoutJson["_id"].toString())["\$oid"].toString()
            )
        }

        return workoutArray
    }
}

@Composable
fun AlertDialogConnection(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    onDismissButton: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {

    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Try Again")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissButton()
                }
            ) {
                Text("Go to Settings")
            }
        }
    )
}