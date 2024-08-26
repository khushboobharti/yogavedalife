package com.yogaveda.data

import com.yogaveda.models.AdminUser
import com.yogaveda.models.Category
import com.yogaveda.models.Newsletter
import com.yogaveda.models.Post
import com.yogaveda.models.PostWithoutDetails
import com.yogaveda.models.User

interface MongoRepository {

    suspend fun checkUserExistence(email: String): User?
    suspend fun updateUserLoginData(user: User): Boolean

    suspend fun checkAdminUserExistence(adminUser: AdminUser): AdminUser?
    suspend fun checkUserId(id: String): Boolean

    suspend fun addUser(user: User): Boolean?

    suspend fun addAdminUser(adminUser: AdminUser): Boolean?

    suspend fun listAdminUsers(): List<AdminUser>?

    suspend fun updateAdminUser(adminUser: AdminUser): Boolean?

    suspend fun deactivateAdminUser(adminUserId: Int): Boolean?

    suspend fun addPost(post: Post): Boolean
    suspend fun updatePost(post: Post): Boolean
    suspend fun readMainPosts(skip: Int): List<PostWithoutDetails>
    suspend fun readMyPosts(skip: Int, author: String): List<PostWithoutDetails>
    suspend fun readLatestPosts(skip: Int): List<PostWithoutDetails>
    suspend fun readPopularPosts(skip: Int): List<PostWithoutDetails>
    suspend fun readSponsoredPosts(): List<PostWithoutDetails>
    suspend fun deleteSelectedPosts(ids: List<String>): Boolean
    suspend fun searchPostsByTittle(query: String, skip: Int): List<PostWithoutDetails>
    suspend fun searchPostsByCategory(category: Category, skip: Int): List<PostWithoutDetails>
    suspend fun readSelectedPost(id: String): Post
    suspend fun subscribe(newsletter: Newsletter): String
}