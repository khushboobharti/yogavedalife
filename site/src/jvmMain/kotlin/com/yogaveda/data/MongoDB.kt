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
import com.varabyte.kobweb.api.env.isProd
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import com.yogaveda.Constants.POSTS_PER_PAGE
import com.yogaveda.models.AdminUser
import com.yogaveda.models.Category
import com.yogaveda.models.Newsletter
import com.yogaveda.models.Post
import com.yogaveda.models.PostWithoutDetails
import com.yogaveda.models.User
import com.yogaveda.util.Constants.DATABASE_NAME
import com.yogaveda.util.Constants.MAIN_POSTS_LIMIT
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList

@InitApi
fun initMongoDB(context: InitApiContext) {
    context.data.add(MongoDB(context))
}

class MongoDB(private val context: InitApiContext) : MongoRepository {

    private val uri = context.getDBConnectionString()
    private val mongoClient = MongoClient.create(uri)
    private val db = mongoClient.getDatabase(DATABASE_NAME)

    private val userCollection = db.getCollection<User>("user")
    private val postCollection = db.getCollection<Post>("post")
    private val newsletterCollection = db.getCollection<Newsletter>("newsletter")
    private val adminUserCollection = db.getCollection<AdminUser>("admin_user")
    private val roleCollection = db.getCollection<Newsletter>("role")

    init {
        context.logger.info("Connecting to MongoDB at ${System.getenv("MONGODB_SERVER")}")
    }

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

    override suspend fun readMainPosts(skip: Int): List<PostWithoutDetails> {
        return postCollection
            .withDocumentClass<PostWithoutDetails>()
            .find(eq(PostWithoutDetails::main.name, true))
            .sort(descending(PostWithoutDetails::date.name))
            .limit(MAIN_POSTS_LIMIT)
            .toList()
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

    override suspend fun readLatestPosts(skip: Int): List<PostWithoutDetails> {
        return postCollection
            .withDocumentClass<PostWithoutDetails>()
            .find(
                and(
                    eq(PostWithoutDetails::main.name, false),
                    eq(PostWithoutDetails::popular.name, false),
                    eq(PostWithoutDetails::sponsored.name, false)
                )
            )
            .sort(descending(PostWithoutDetails::date.name))
            .skip(skip)
            .limit(POSTS_PER_PAGE)
            .toList()
    }

    override suspend fun readPopularPosts(skip: Int): List<PostWithoutDetails> {
        return postCollection
            .withDocumentClass<PostWithoutDetails>()
            .find(eq(PostWithoutDetails::popular.name, true))
            .sort(descending(PostWithoutDetails::date.name))
            .skip(skip)
            .limit(POSTS_PER_PAGE)
            .toList()
    }

    override suspend fun readSponsoredPosts(): List<PostWithoutDetails> {
        return postCollection
            .withDocumentClass<PostWithoutDetails>()
            .find(eq(PostWithoutDetails::sponsored.name, true))
            .sort(descending(PostWithoutDetails::date.name))
            .limit(2)
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

    override suspend fun searchPostsByCategory(
        category: Category,
        skip: Int
    ): List<PostWithoutDetails> {
        return postCollection
            .withDocumentClass(PostWithoutDetails::class.java)
            .find(eq(PostWithoutDetails::category.name, category.name))
            .sort(descending(PostWithoutDetails::date.name))
            .skip(skip)
            .limit(POSTS_PER_PAGE)
            .toList()
    }

    // User APIs
    override suspend fun checkUserExistence(email: String): User? {
        return try {
            userCollection.find(
                and(
                    eq(User::email.name, email)
                )
            ).firstOrNull()
        } catch (e: Exception) {
            context.logger.error("Database Exception: " + e.message.toString())
            return null
        }
    }

    override suspend fun updateUserLoginData(user: User): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun checkAdminUserExistence(adminUser: AdminUser): AdminUser? {
        return try {
            adminUserCollection.find(
                and(
                    eq(AdminUser::username.name, adminUser.username),
                    eq(AdminUser::password.name, adminUser.password)
                )
            ).firstOrNull()
        } catch (e: Exception) {
            context.logger.error("Database Exception: " + e.message.toString())
            return null
        }
    }

    override suspend fun checkUserId(id: String): Boolean {
        return try {
            val documentCount = adminUserCollection.countDocuments(eq("_id", id), CountOptions().limit(1))
            documentCount > 0
        } catch (e: Exception) {
            context.logger.error("Database Exception: " + e.message.toString())
            false
        }
    }

    override suspend fun addUser(user: User): Boolean? {
        // check if user already exists
        return try {
            val newUser = userCollection.find(
                and(
                    eq(User::email.name, user.email)
                )
            ).firstOrNull()
            if(newUser == null) {
                userCollection.insertOne(user).wasAcknowledged()
            } else false
        } catch (e: Exception) {
            context.logger.error("Database Exception: " + e.message.toString())
            false
        }
    }


    override suspend fun addAdminUser(adminUser: AdminUser): Boolean? {
        return try {
            val newAdminUser = adminUserCollection.find(
                and(
                    eq(AdminUser::username.name, adminUser.username),
                    eq(AdminUser::password.name, adminUser.password)
                )
            ).firstOrNull()
            if(newAdminUser == null) {
                adminUserCollection.insertOne(adminUser).wasAcknowledged()
            } else false
        } catch (e: Exception) {
            context.logger.error("Database Exception: " + e.message.toString())
            return null
        }
    }

    override suspend fun listAdminUsers(): List<AdminUser>? {
        return try {
            adminUserCollection.find().toList()
        } catch (e: Exception) {
            context.logger.error("Database Exception: " + e.message.toString())
            return null
        }
    }

    override suspend fun updateAdminUser(adminUser: AdminUser): Boolean? {
        return try {
            adminUserCollection
                .updateOne(
                    eq(AdminUser::id.name, adminUser.id),
                    mutableListOf(
                        Updates.set(AdminUser::password.name, adminUser.password)
                    )
                ).wasAcknowledged()
        } catch (e: Exception) {
            context.logger.error("Database Exception: " + e.message.toString())
            return null
        }
    }

    override suspend fun deactivateAdminUser(adminUserId: Int): Boolean? {
        return try {
            adminUserCollection
                .updateOne(
                    eq(AdminUser::id.name, adminUserId),
                    mutableListOf(
                        Updates.set(AdminUser::password.name, "deactivated")
                    )
                ).wasAcknowledged()
        } catch (e: Exception) {
            context.logger.error("Database Exception: " + e.message.toString())
            return null
        }
    }

    override suspend fun readSelectedPost(id: String): Post {
        return postCollection.find(eq(Post::_id.name, id)).toList().first()
    }

    override suspend fun subscribe(newsletter: Newsletter): String {
        val result = newsletterCollection
            .find(eq(Newsletter::email.name, newsletter.email))
            .toList()
        return if (result.isNotEmpty()) {
            "You are already subscribed."
        } else {
            val newEmail = newsletterCollection
                .insertOne(newsletter)
                .wasAcknowledged()
            if(newEmail) "Successfully Subscribed!"
            else "Something went wrong!"
        }
    }
}

fun InitApiContext.getDBConnectionString(): String {
    return when {
        this.env.isProd -> "mongodb+srv://${System.getenv("DB_USER")}:${System.getenv("DB_PASSWORD")}@${System.getenv("DB_URL")}/" // System.getenv("MONGODB_SERVER")  // "mongodb+srv://${System.getenv("DB_USER")}:${System.getenv("DB_PASSWORD")}.${System.getenv("DB_PASSWORD")}/"
        else -> "mongodb://localhost:27017"
    }
}