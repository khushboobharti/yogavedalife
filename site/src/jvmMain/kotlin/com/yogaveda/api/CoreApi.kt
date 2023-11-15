package com.yogaveda.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import com.yogaveda.data.MongoDB
import com.yogaveda.models.User
import com.yogaveda.models.UserWithoutPassword
import com.yogaveda.util.getHash
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Api(routeOverride = "usercheck")
suspend fun userCheck(context: ApiContext) {
    try {
        // Get user to be queried from request
        val userRequest = context.req.body?.decodeToString()?.let {
            Json.decodeFromString<User>(it)
        }
        // Query user from DB
        val user = userRequest?.let {
            context.data.getValue<MongoDB>().checkUserExistence(
                User(id = "", username = it.username, password = it.password.getHash())
            )
        }

        // Return response based on the result from DB
        if(user != null) {
            context.res.setBodyText(
                Json.encodeToString(
                    UserWithoutPassword(id = user.id, username = user.username)
                )
            )
        } else {
            context.res.setBodyText(Json.encodeToString(Exception("User does not exist.")))
        }
    } catch(e: Exception) {
        context.res.setBodyText(Json.encodeToString(Exception(e.message)))
    }
}