package com.yogaveda.data

import com.mongodb.client.model.CountOptions
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Filters.`in`
import com.mongodb.client.model.Sorts.ascending
import com.mongodb.client.model.Sorts.descending
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import com.yogaveda.models.Post
import com.yogaveda.models.PostWithoutDetails
import com.yogaveda.models.User
import com.yogaveda.Constants.POSTS_PER_PAGE
import com.yogaveda.util.Constants.DATABASE_NAME
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

    override suspend fun updatePost(post: Post): Boolean {
        return postCollection
            .updateOne(
                eq(Post::_id.name, post._id),
                mutableListOf(
                    Updates.set(Post::title.name, post.title),
                    Updates.set(Post::subtitle.name, post.subtitle),
                    Updates.set(Post::category.name, post.category),
                    Updates.set(Post::thumbnail.name, post.thumbnail),
                    Updates.set(Post::content.name, post.content),
                    Updates.set(Post::main.name, post.main),
                    Updates.set(Post::popular.name, post.popular),
                    Updates.set(Post::sponsored.name, post.sponsored)
                )
            ).wasAcknowledged()
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

    override suspend fun deleteSelectedPosts(ids: List<String>): Boolean {
        return try {
            postCollection
                .deleteMany(filter = `in`(Post::_id.name, ids))
                .wasAcknowledged()
        } catch (e: Exception) {
            context.logger.error("Database Exception: " + e.message.toString())
            false
        }
    }

    override suspend fun searchPostsByTittle(query: String, skip: Int): List<PostWithoutDetails> {
        val regexQuery = query.toRegex(RegexOption.IGNORE_CASE)
        return postCollection
            .withDocumentClass(PostWithoutDetails::class.java)
            .find(Filters.regex(PostWithoutDetails::title.name, regexQuery.pattern))
            .sort(descending(PostWithoutDetails::date.name))
            .skip(skip)
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

    override suspend fun readSelectedPost(id: String): Post {
        return postCollection.find(eq(Post::_id.name, id)).toList().first()
    }
}