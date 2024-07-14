package com.yogaveda.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.ObjectIdGenerator
import org.bson.codecs.pojo.annotations.BsonId

@Serializable
actual data class AdminUser(

    @BsonId
    @SerialName("_id")
    actual val id: String = ObjectIdGenerator().generate().toString(),
    actual val username: String = "",
    actual val password: String = ""
)

@Serializable
actual data class AdminUserWithoutPassword(
    @BsonId
    @SerialName("_id")
    actual val id: String = ObjectIdGenerator().generate().toString(),
    actual val username: String = ""
)