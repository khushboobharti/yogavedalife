package com.yogaveda.network

import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.browser.http.http
import com.yogaveda.Constants.AUTHOR_PARAM
import com.yogaveda.Constants.CATEGORY_PARAM
import com.yogaveda.Constants.POST_ID_PARAM
import com.yogaveda.Constants.QUERY_PARAM
import com.yogaveda.Constants.SKIP_PARAM
import com.yogaveda.models.AdminUser
import com.yogaveda.models.AdminUserWithoutPassword
import com.yogaveda.models.ApiListResponse
import com.yogaveda.models.ApiResponse
import com.yogaveda.models.Category
import com.yogaveda.models.Newsletter
import com.yogaveda.models.Post
import com.yogaveda.models.RandomJoke
import com.yogaveda.models.User
import com.yogaveda.util.Constants
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.js.Date

suspend fun checkUserExistence(adminUser: AdminUser): AdminUserWithoutPassword? {
    return try {
        //println("AdminUser Binary: " + Json.encodeToString(adminUser))
        val result = window.api.tryPost(
            apiPath = "usercheck",
            body = Json.encodeToString(adminUser).encodeToByteArray()
        )
        result?.decodeToString()?.let { Json.decodeFromString<AdminUserWithoutPassword>(it) }
    } catch (e: Exception) {
        println(e.message)
        null
    }
}

suspend fun checkUserId(id: String): Boolean {
    return try {
        println("Check UserID: " + Json.encodeToString(id))
        window.api.tryPost(
            apiPath = "checkuserid",
            body = Json.encodeToString(id).encodeToByteArray()
        )?.decodeToString()?.parseData<Boolean>() ?: false
    } catch (e: Exception) {
        println(e.message.toString())
        false
    }
}

suspend fun userLogin(user: User): User? {
    return try {
        val result = window.api.tryPost(
            apiPath = "userlogin",
            body = Json.encodeToString(user).encodeToByteArray()
        )
        result?.decodeToString()?.let { Json.decodeFromString<User>(it) }
    } catch (e: Exception) {
        println(e.message)
        null
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
                onComplete(result.parseData())
                localStorage["date"] = Date.now().toString()
                localStorage["joke"] = result
            } catch (e: Exception) {
                onComplete(RandomJoke(id = -1, joke = e.message.toString()))
                println(e.message)
            }

        } else {
            try {
                localStorage["joke"]?.parseData<RandomJoke>()?.let { onComplete(it) }
            } catch (e: Exception) {
                onComplete(RandomJoke(id = -1, joke = e.message.toString()))
                println(e.message)
            }
        }
    } else {
        try {
            val result = window.http.get(Constants.HUMOR_API_URL).decodeToString()
            println(result)
            onComplete(result.parseData())
            localStorage["date"] = Date.now().toString()
            localStorage["joke"] = result
        } catch (e: Exception) {
            onComplete(RandomJoke(id = -1, joke = e.message.toString()))
            println(e.message)
        }
    }
}

suspend fun addPost(post: Post): Boolean {
    return try {
        window.api.tryPost(
            apiPath = "addPost",
            body = Json.encodeToString(post).encodeToByteArray()
        )?.decodeToString().toBoolean()
    } catch (e: Exception) {
        println(e.message.toString())
        false
    }
}

suspend fun fetchMyPosts(
    skip: Int,
    onSuccess: (ApiListResponse) -> Unit,
    onError: (Exception) -> Unit
) {
    try {
        val result = window.api.tryGet(
            apiPath = "readmyposts?$SKIP_PARAM=$skip&$AUTHOR_PARAM=${localStorage["username"]}",
        )?.decodeToString()
        onSuccess(result.parseData())
    } catch (e: Exception) {
        onError(e)
    }
}

suspend fun fetchMainPosts(
    skip: Int,
    onSuccess: (ApiListResponse) -> Unit,
    onError: (Exception) -> Unit
) {
    try {
        val result = window.api.tryGet(
            apiPath = "readmainposts?$SKIP_PARAM=$skip",
        )?.decodeToString()
        //println(result)
        onSuccess(result.parseData())
    } catch (e: Exception) {
        onError(e)
    }
}

suspend fun fetchLatestPosts(
    skip: Int,
    onSuccess: (ApiListResponse) -> Unit,
    onError: (Exception) -> Unit
) {
    try {
        val result = window.api.tryGet(
            apiPath = "readlatestposts?$SKIP_PARAM=$skip",
        )?.decodeToString()
        //println(result)
        onSuccess(result.parseData())
    } catch (e: Exception) {
        onError(e)
    }
}

suspend fun fetchPopularPosts(
    skip: Int,
    onSuccess: (ApiListResponse) -> Unit,
    onError: (Exception) -> Unit
) {
    try {
        val result = window.api.tryGet(
            apiPath = "readpopularposts?$SKIP_PARAM=$skip",
        )?.decodeToString()
        onSuccess(result.parseData())
    } catch (e: Exception) {
        onError(e)
    }
}

suspend fun fetchSponsoredPosts(
    onSuccess: (ApiListResponse) -> Unit,
    onError: (Exception) -> Unit
) {
    try {
        val result = window.api.tryGet(
            apiPath = "readsponsoredposts",
        )?.decodeToString()
        onSuccess(result.parseData())
    } catch (e: Exception) {
        println(e.message)
        onError(e)
    }
}

suspend fun updatePost(post: Post): Boolean {
    return try {
        window.api.tryPost(
            apiPath = "updatepost",
            body = Json.encodeToString(post).encodeToByteArray()
        )?.decodeToString().toBoolean()
    } catch (e: Exception) {
        println(e.message)
        false
    }
}

suspend fun deleteSelectedPosts(ids: List<String>): Boolean {
    return try {
        val result = window.api.tryPost(
            apiPath = "deleteselectedposts",
            body = Json.encodeToString(ids).encodeToByteArray()
        )?.decodeToString()
        result.toBoolean()
    } catch (e: Exception) {
        println(e.message)
        false
    }
}

suspend fun searchPostsByTitle(
    query: String,
    skip: Int,
    onSuccess: (ApiListResponse) -> Unit,
    onError: (Exception) -> Unit
) {
    try {
        val result = window.api.tryGet(
            apiPath = "searchposts?${QUERY_PARAM}=$query&${SKIP_PARAM}=$skip"
        )?.decodeToString()
        onSuccess(result.parseData())
    } catch (e: Exception) {
        println(e.message)
        onError(e)
    }
}

suspend fun searchPostsByCategory(
    category: Category,
    skip: Int,
    onSuccess: (ApiListResponse) -> Unit,
    onError: (Exception) -> Unit
) {
    try {
        val result = window.api.tryGet(
            apiPath = "searchpostsbycategory?${CATEGORY_PARAM}=${category.name}&${SKIP_PARAM}=$skip"
        )?.decodeToString()
        onSuccess(result.parseData())
    } catch (e: Exception) {
        println(e.message)
        onError(e)
    }
}


suspend fun fetchSelectedPost(id: String): ApiResponse {
    return try {
        val result = window.api.tryGet(
            apiPath = "readselectedpost?${POST_ID_PARAM}=$id"
        )?.decodeToString()
        result?.parseData() ?: ApiResponse.Error(message = "Result is null")
    } catch (e: Exception) {
        println(e)
        ApiResponse.Error(message = e.message.toString())
    }
}

suspend fun subscribeToNewsletter(newsletter: Newsletter): String {

    return try {
        val result = window.api.tryPost(
            apiPath = "subscribe-newsletter",
            body = Json.encodeToString(newsletter).encodeToByteArray()
        )?.decodeToString()
        result.toString().replace("\"", "")
    } catch (e: Exception) {
        println(e.message)
        e.message.toString()
    }
}

suspend fun signInUser() {

}

inline fun <reified T> String?.parseData(): T {
    return Json.decodeFromString(this.toString())
}