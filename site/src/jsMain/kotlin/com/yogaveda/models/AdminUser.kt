package com.yogaveda.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
actual data class AdminUser(
    @SerialName("_id")
    actual val id: String = "",
    actual val username: String = "",
    actual val password: String = ""
)

@Serializable
actual data class AdminUserWithoutPassword(
    @SerialName("_id")
    actual val id: String = "",
    actual val username: String = ""
)