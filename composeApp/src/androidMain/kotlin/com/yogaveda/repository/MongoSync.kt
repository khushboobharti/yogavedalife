package com.yogaveda.repository

import MONGODB_APP_ID
import com.yogaveda.model.Category
import com.yogaveda.model.Post
import com.yogaveda.model.RequestState
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
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
                flow { emit(RequestState.Error(Exception(e))) }
            }
        } else {
            flow { emit(RequestState.Error(Exception("User not found"))) }
        }
    }

    override fun searchPostsByTitle(query: String): Flow<RequestState<List<Post>>> {
        return if(user != null) {
            try {
                realm.query<Post>(query = "title CONTAINS[c] $0", query)
                    .asFlow()
                    .map {
                        RequestState.Success(data = it.list)
                    }
            } catch (e: Exception) {
                flow { emit(RequestState.Error(Exception(e))) }
            }
        } else {
            flow { emit(RequestState.Error(Exception("User not found"))) }
        }
    }

    override fun searchPostsByCategory(category: Category): Flow<RequestState<List<Post>>> {
        return if(user != null) {
            try {
                realm.query<Post>(query = "category == $0", category.name)
                    .asFlow()
                    .map {
                        RequestState.Success(data = it.list)
                    }
            } catch (e: Exception) {
                flow { emit(RequestState.Error(Exception(e))) }
            }
        } else {
            flow { emit(RequestState.Error(Exception("User not found"))) }
        }
    }
}