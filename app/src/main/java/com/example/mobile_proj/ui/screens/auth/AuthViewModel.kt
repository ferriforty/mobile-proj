package com.example.mobile_proj.ui.screens.auth

import com.example.mobile_proj.database.Connection
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import org.json.JSONObject

fun signInCheck(credentials: CredentialsSignIn, db: Connection): Boolean {
    var res: JSONObject
    runBlocking {
        res = db.signIn(credentials.username, credentials.pwd, credentials.remember)
    }
    return JSONObject(res["code"].toString())["\$numberLong"] == "200"
}

fun signInCheckWithToken(username: String, authToken: String, db: Connection): Boolean {
    var res: JSONObject
    runBlocking {
        res = db.signInToken(username, authToken)
    }
    return JSONObject(res["code"].toString())["\$numberLong"] == "200"
}

fun signUpCheck(credentials: CredentialsSignUp, db: Connection): Boolean {
    var res: JSONObject
    val creds = JSONObject(Gson().toJson(credentials))
    runBlocking {
        res = db.signUp(creds)
    }
    return JSONObject(res["code"].toString())["\$numberLong"] == "200"
}

fun userExists(username: String, db: Connection): Boolean {
    return runBlocking {
        return@runBlocking db.userExists(username)
    }
}