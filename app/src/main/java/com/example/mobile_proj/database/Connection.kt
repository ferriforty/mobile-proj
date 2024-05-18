package com.example.mobile_proj.database

import android.content.Context
import com.example.mobile_proj.BuildConfig
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.User
import io.realm.kotlin.mongodb.ext.call
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

class Connection() {
    private val app: App = App.create(BuildConfig.APP_ID);
    private var user: User;

    init {
        runBlocking {
            val credentials = Credentials.emailPassword(BuildConfig.EMAIL, BuildConfig.PASSWORD);
            user = app.login(credentials)
        }
    }

    suspend fun insertUser(arg: JSONObject, context: Context): Int {
        arg.put("password", generateHash(arg.get("password").toString()))
        return user.functions.call<Int>("insert_user_gymShred", arg.toString())
    }
}