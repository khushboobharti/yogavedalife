package com.yogaveda.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import com.yogaveda.data.MongoDB
import com.yogaveda.models.ApiListResponse
import com.yogaveda.models.ApiResponse
import com.yogaveda.models.Post
import com.yogaveda.util.Constants.POST_ID_PARAM
import com.yogaveda.util.Constants.QUERY_PARAM
import com.yogaveda.util.Constants.SKIP_PARAM
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonEncoder
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

@Api(routeOverride = "deleteselectedposts")
suspend fun deleteSelectedPosts(context: ApiContext) {
    try {
        val ids = context.req.body?.decodeToString()?.let { Json.decodeFromString<List<String>>(it) }
        context.res.setBodyText(
            ids?.let {
                it.forEach { id ->
                    context.logger.debug("ID received: $id")
                }
                context.data.getValue<MongoDB>().deleteSelectedPosts(ids = it).toString()
            } ?: false.toString()
        )
    } catch (e: Exception) {
        e.message?.let { context.logger.error(it) }
        context.res.setBodyText(Json.encodeToString(value = e.message))
    }
}

@Api(routeOverride = "searchposts")
suspend fun searchPostsByTitle(context: ApiContext) {
    try {
        val query = context.req.params[QUERY_PARAM] ?: ""
        val skip = context.req.params[SKIP_PARAM]?.toInt() ?: 0
        val posts = context.data.getValue<MongoDB>().searchPostsByTittle(
            query = query,
            skip = skip
        )
        context.res.setBodyText(Json.encodeToString(ApiListResponse.Success(data = posts)))
    } catch (e: Exception) {
        context.res.setBodyText(Json.encodeToString(ApiListResponse.Error(message = e.message.toString())))
    }
}

@Api(routeOverride = "readselectedpost")
suspend fun readSelectedPost(context: ApiContext) {
    val postId = context.req.params[POST_ID_PARAM]
    if (!postId.isNullOrEmpty()) {
        try {
            val selectedPost = context.data.getValue<MongoDB>().readSelectedPost(id = postId)
            context.res.setBodyText(Json.encodeToString(ApiResponse.Success(data = selectedPost)))
        } catch (e: Exception) {
            context.res.setBodyText(Json.encodeToString(ApiResponse.Error(message = e.message.toString())))
        }
    } else {
        context.res.setBodyText(Json.encodeToString(ApiResponse.Error(message = "Selected Post does not exist.")))
    }
}