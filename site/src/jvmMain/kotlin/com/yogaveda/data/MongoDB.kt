package com.yogaveda.data

import com.mongodb.client.model.CountOptions
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import com.yogaveda.models.Post
import com.yogaveda.models.User
import kotlinx.coroutines.flow.firstOrNull

@InitApi
fun initMongoDB(context: InitApiContext) {
    context.data.add(MongoDB(context))
}

class MongoDB(private val context: InitApiContext) : MongoRepository {

    // Replace the placeholder with your MongoDB deployment's connection string
    private val uri = "mongodb://localhost:27017"
    private val mongoClient = MongoClient.create(uri)
    private val db = mongoClient.getDatabase("yogaveda_db")

    private val userCollection = db.getCollection<User>("users")
    private val postCollection = db.getCollection<Post>("posts")

    override suspend fun addPost(post: Post): Boolean {
        return postCollection.insertOne(post).wasAcknowledged()
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