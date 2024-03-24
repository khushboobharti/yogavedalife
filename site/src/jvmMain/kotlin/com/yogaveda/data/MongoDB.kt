package com.yogaveda.data

import com.mongodb.client.model.CountOptions
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Sorts.ascending
import com.mongodb.client.model.Sorts.descending
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import com.yogaveda.models.Post
import com.yogaveda.models.PostWithoutDetails
import com.yogaveda.models.User
import com.yogaveda.util.Constants.DATABASE_NAME
import com.yogaveda.util.Constants.POSTS_PER_PAGE
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList

@InitApi
fun initMongoDB(context: InitApiContext) {
    context.data.add(MongoDB(context))
}

class MongoDB(private val context: InitApiContext) : MongoRepository {

    // Replace the placeholder with your MongoDB deployment's connection string
    private val host = System.getenv("KOBWEB_MONGO_SERVER") ?: "localhost"
    private val uri = "mongodb://$host:27017"
    private val mongoClient = MongoClient.create(uri)
    private val db = mongoClient.getDatabase(DATABASE_NAME)

    private val userCollection = db.getCollection<User>("users")
    private val postCollection = db.getCollection<Post>("posts")

    override suspend fun addPost(post: Post): Boolean {
        return postCollection.insertOne(post).wasAcknowledged()
    }

    override suspend fun readMyPosts(skip: Int, author: String): List<PostWithoutDetails> {
        return postCollection
            .withDocumentClass<PostWithoutDetails>()
            .find(eq(PostWithoutDetails::author.name, author))
            .skip(skip)
            .sort(ascending(PostWithoutDetails::_id.name))
            .limit(POSTS_PER_PAGE)
            .toList()
    }

    override suspend fun checkUserExistence(user: User): User? {
        return try {
            userCollection.find(
                and(
                    eq(User::username.name, user.username),
                    eq(User::password.name, user.password)
                )
            ).firstOrNull()
        } catch (e: Exception) {
            context.logger.error("Database Exception: " + e.message.toString())
            return null
        }
    }

    override suspend fun checkUserId(id: String): Boolean {
        return try {
            val documentCount = userCollection.countDocuments(eq("_id", id), CountOptions().limit(1))
            documentCount > 0
        } catch (e: Exception) {
            context.logger.error("Database Exception: " + e.message.toString())
            false
        }
    }
}