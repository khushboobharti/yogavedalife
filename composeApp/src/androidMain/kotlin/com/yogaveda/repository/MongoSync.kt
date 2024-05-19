package com.yogaveda.repository

import MONGODB_APP_ID
import com.yogaveda.model.RequestState
import com.yogaveda.models.Post
import io.realm.kotlin.Realm
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.flow.Flow

object MongoSync: MongoSyncRepository {

    val mongoApp = App.create(MONGODB_APP_ID)
    val user = mongoApp.currentUser
    private lateinit var realm : Realm

    init {
        configureTheRealm()
    }
    override fun configureTheRealm() {
        /*if(user != null) {
            val config = SyncConfiguration.Builder(user, setOf(PostSync::class))
                .initialSubscriptions {
                    add(
                        query = it.query(PostSync::class),
                        name = "Blog Posts"
                    )
                }
                .log(LogLevel.ALL)
                .build()
            realm = Realm.open(config)
        }*/
    }

    override fun readAllPosts(): Flow<RequestState<List<Post>>> {
        TODO("Not yet implemented")
    }
}