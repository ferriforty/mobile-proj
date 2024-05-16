package com.example.mobile_proj.database

import com.example.mobile_proj.BuildConfig
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.User
import io.realm.kotlin.mongodb.ext.call
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.mongodb.kbson.BsonArray

class Connection() {
    private val app: App = App.create(BuildConfig.APP_ID);
    private var user: User;

    init {
        runBlocking {
            val credentials = Credentials.emailPassword(BuildConfig.EMAIL, BuildConfig.PASSWORD);
            user = app.login(credentials)
        }
    }

    suspend fun getUsers(): JSONArray {
        return JSONArray(user.functions.call<BsonArray>("getUsers", "").toJson())
    }

}