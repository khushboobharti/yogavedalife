package com.yogaveda.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import com.yogaveda.data.MongoDB
import com.yogaveda.models.AdminUser
import com.yogaveda.models.AdminUserWithoutPassword
import com.yogaveda.models.User
import com.yogaveda.util.getHash
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bson.codecs.ObjectIdGenerator

@Api(routeOverride = "usercheck")
suspend fun userCheck(context: ApiContext) {
    try {
        // Get user to be queried from request
        val userRequest = context.req.body?.decodeToString()?.let {
            context.logger.debug(it)
            Json.decodeFromString<AdminUser>(it)
        }
        // Query user from DB
        val user = userRequest?.let {
            context.logger.debug("Password Hash: " + it.password.getHash())
            context.data.getValue<MongoDB>().checkAdminUserExistence(
                AdminUser(username = it.username, password = it.password.getHash())
            )
        }

        context.logger.debug("User: $user")

        // Return response based on the result from DB
        if(user != null) {
            context.logger.debug("User is not null")
            context.res.setBodyText(
                Json.encodeToString(
                    AdminUserWithoutPassword(id = user.id, username = user.username)
                )
            )
        } else {
            context.logger.debug("User is null")
            context.res.setBodyText(Json.encodeToString("User does not exist."))
        }
    } catch(e: Exception) {
        context.logger.debug("Exception: " + e.message.toString())
        context.res.setBodyText(Json.encodeToString(e.message))
    }
}

@Api(routeOverride = "checkuserid")
suspend fun checkUserId(context: ApiContext) {
    try {
        val userId = context.req.body?.decodeToString()?.let { Json.decodeFromString<String>(it) }
        val result = userId?.let {
            context.data.getValue<MongoDB>().checkUserId(it)
        }

        if(result != null) {
            context.res.setBodyText(Json.encodeToString(result))
        } else {
            context.res.setBodyText(Json.encodeToString(false))
        }
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(false))
    }
}

@Api(routeOverride = "userlogin")
suspend fun updateUserLoginData(context: ApiContext) {
    try {
        // If user does not exists then create otherwise sign in existing user
        val user = context.req.body?.decodeToString()?.let {
            context.logger.debug(it)
            Json.decodeFromString<User>(it)
        }
        context.logger

        val oldUser = user?.let {
            context.data.getValue<MongoDB>().checkUserExistence(it.email)
        }

        if(oldUser == null) {
            val newUser = user?.copy(id = ObjectIdGenerator().generate().toString())
            val result = newUser?.let {
                context.data.getValue<MongoDB>().addUser(newUser)
            }
            if(result != null && result) {
                context.res.setBodyText(Json.encodeToString(newUser))
            } else {
                context.res.setBodyText(Json.encodeToString("failed to insert user"))
            }
        } else {
            val result = oldUser?.let {
                context.data.getValue<MongoDB>().updateUserLoginData(it)
            }
            if(result != null && result) {
                context.res.setBodyText(Json.encodeToString("user updated successfully"))
            } else {
                context.res.setBodyText(Json.encodeToString("failed to update user"))
            }
        }
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(e.message))
    }
}