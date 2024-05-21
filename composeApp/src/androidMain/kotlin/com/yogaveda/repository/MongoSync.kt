package com.yogaveda.repository

import MONGODB_APP_ID
import com.yogaveda.model.Post
import com.yogaveda.model.RequestState
import io.realm.kotlin.Realm
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

object MongoSync: MongoSyncRepository {

    val mongoApp = App.create(MONGODB_APP_ID)
    val user = mongoApp.currentUser
    private lateinit var realm : Realm

    init {
        configureTheRealm()
    }
    override fun configureTheRealm() {
        if(user != null) {
            val config = SyncConfiguration.Builder(user, setOf(Post::class))
                .initialSubscriptions {
                    add(
                        query = it.query(Post::class),
                        name = "Blog Posts"
                    )
                }
                .log(LogLevel.ALL)
                .build()
            realm = Realm.open(config)
        }
    }

    override fun readAllPosts(): Flow<RequestState<List<Post>>> {
        return if(user != null) {
            try {
                realm.query(Post::class)
                    .asFlow()
                    .map {
                        RequestState.Success(data = it.list)
                    }
            } catch (e: Exception) {
                flow { RequestState.Error(Exception(e)) }
            }
        } else {
            flow { RequestState.Error(Exception("User not found")) }
        }
    }
}