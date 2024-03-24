package com.yogaveda.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import com.yogaveda.data.MongoDB
import com.yogaveda.models.ApiListResponse
import com.yogaveda.models.Post
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bson.codecs.ObjectIdGenerator

@Api(routeOverride = "addPost")
suspend  fun addPost(context: ApiContext) {
    try {
        val post = context.req.body?.decodeToString()?.let { Json.decodeFromString<Post>(it) }
        val newPost = post?.copy(_id = ObjectIdGenerator().generate().toString())
        context.res.setBodyText(
            newPost?.let{
                context.data.getValue<MongoDB>().addPost(it).toString()
            } ?: false.toString()
        )
    } catch (e: Exception) {
        e.message?.let { context.logger.error(it) }
        context.res.setBodyText(Json.encodeToString(value = e.message))
    }
}

@Api(routeOverride = "readmyposts")
suspend fun readMyPosts(context: ApiContext) {
    try {
        val skip = context.req.params["skip"]?.toInt() ?: 0
        val author = context.req.params["author"] ?: ""
        val posts = context.data.getValue<MongoDB>().readMyPosts(skip = skip, author = author)
        context.res.setBodyText(
            Json.encodeToString(ApiListResponse.Success(posts))
        )
    } catch (e: Exception) {
        e.message?.let { context.logger.error(it) }
        context.res.setBodyText(Json.encodeToString(value = e.message.toString()))
    }
}