package com.yogaveda.repository

import com.yogaveda.model.RequestState
import com.yogaveda.models.Post
import kotlinx.coroutines.flow.Flow

interface MongoSyncRepository {
    fun configureTheRealm()
    fun readAllPosts(): Flow<RequestState<List<Post>>>
}