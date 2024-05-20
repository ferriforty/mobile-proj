package com.example.mobile_proj.ui.screens.auth

import com.example.mobile_proj.database.Connection
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

fun signInCheck(credentials: Credentials, db: Connection): Boolean {
    var res: JSONObject
    runBlocking {
        res = db.signIn(credentials.username, credentials.pwd, credentials.remember)
    }
    return JSONObject(res["code"].toString())["\$numberLong"] == "200"
}