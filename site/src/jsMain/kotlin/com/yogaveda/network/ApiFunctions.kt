package com.yogaveda.network

import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.compose.http.http
import com.yogaveda.models.RandomJoke
import com.yogaveda.models.User
import com.yogaveda.models.UserWithoutPassword
import com.yogaveda.util.Constants
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.js.Date

suspend fun checkUserExistence(user: User): UserWithoutPassword? {
    return try {
        val result = window.api.tryPost(
            apiPath = "usercheck",
            body = Json.encodeToString(user).encodeToByteArray()
        )
        result?.decodeToString()?.let { Json.decodeFromString<UserWithoutPassword>(it) }
    } catch (e: Exception) {
        println(e.message)
        null
    }
}

suspend fun checkUserId(id: String): Boolean {
    return try {
        val result = window.api.tryPost(
            apiPath = "checkuserid",
            body = Json.encodeToString(id).encodeToByteArray()
        )
        result?.decodeToString()?.let { Json.decodeFromString<Boolean>(it) } ?: false
    } catch (e: Exception) {
        println(e.message.toString())
        false
    }
}

suspend fun fetchRandomJokes(onComplete: (RandomJoke) -> Unit) {
    val date = localStorage["date"]
    if (date != null) {
        val difference = (Date.now() - date.toDouble())
        val dayHasPassed = difference >= 86400000
        if (dayHasPassed) {
            try {
                val result = window.http.get(Constants.HUMOR_API_URL).decodeToString()
                println(result)
                onComplete(Json.decodeFromString(result))
                localStorage["date"] = Date.now().toString()
                localStorage["joke"] = result
            } catch (e: Exception) {
                onComplete(RandomJoke(id = -1, joke = e.message.toString()))
                println(e.message)
            }

        } else {
            try {
                localStorage["joke"]?.let { Json.decodeFromString<RandomJoke>(it) }?.let { onComplete(it) }
            } catch (e: Exception) {
                onComplete(RandomJoke(id = -1, joke = e.message.toString()))
                println(e.message)
            }
        }
    } else {
        try {
            val result = window.http.get(Constants.HUMOR_API_URL).decodeToString()
            println(result)
            onComplete(Json.decodeFromString(result))
            localStorage["date"] = Date.now().toString()
            localStorage["joke"] = result
        } catch (e: Exception) {
            onComplete(RandomJoke(id = -1, joke = e.message.toString()))
            println(e.message)
        }
    }
}