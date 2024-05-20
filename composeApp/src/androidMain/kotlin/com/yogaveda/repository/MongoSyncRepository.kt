package com.yogaveda.repository

import com.yogaveda.model.PostSync
import com.yogaveda.model.RequestState
import kotlinx.coroutines.flow.Flow

interface MongoSyncRepository {
    fun configureTheRealm()
    fun readAllPosts(): Flow<RequestState<List<PostSync>>>
}