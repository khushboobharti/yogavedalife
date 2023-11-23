package com.yogaveda.data

import com.yogaveda.models.User

interface MongoRepository {

    suspend fun checkUserExistence(user: User): User?
    suspend fun checkUserId(id: String): Boolean
}