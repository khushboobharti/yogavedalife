package com.yogaveda.data

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApiContext
import com.yogaveda.models.User
import kotlinx.coroutines.flow.firstOrNull

fun initMongoDB(context: InitApiContext) {

    context.data.add(MongoDB(context))
}

class MongoDB(val context: InitApiContext) : MongoRepository {

    // Replace the placeholder with your MongoDB deployment's connection string
    private val uri = "CONNECTION_STRING_URI_PLACEHOLDER"
    private val mongoClient = MongoClient.create(uri)
    private val db = mongoClient.getDatabase("")

    val userCollection = db.getCollection<User>("users")
    override suspend fun checkUserExistence(user: User): User? {
        return try {
            userCollection.find(
                and(
                    eq(User::username.toString(), user.username),
                    eq(User::password.toString(), user.password)
                )
            ).firstOrNull()
        } catch (e: Exception) {
            context.logger.error(e.message.toString())
            return null
        }
    }
}