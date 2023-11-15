package com.yogaveda.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class User(

    @BsonId
    @SerialName("_id")
    val id: String,
    val username: String = "",
    val password: String = ""
)

data class UserWithoutPassword(
    @BsonId
    @SerialName("_id")
    val id: String,
    val username: String = ""
)
