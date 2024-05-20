package com.example.mobile_proj.ui.screens.auth

import com.example.mobile_proj.database.Connection
import kotlinx.coroutines.runBlocking
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
    runBlocking {
        res = db.signUp(JSONObject(
            """
                {"name": ${credentials.name}},
                {"surname": ${credentials.surname}},
                {"username": ${credentials.username}},
                {"password": ${credentials.pwd}},
                {"birthDate": ${credentials.date}},
                {"profileImage": ${credentials.profileImage}},
            """.trimIndent()),
            credentials.remember)
    }
    return JSONObject(res["code"].toString())["\$numberLong"] == "200"
}