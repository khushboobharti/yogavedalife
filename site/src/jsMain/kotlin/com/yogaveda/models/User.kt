@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.yogaveda.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
actual data class User(
    @SerialName("_id")
    actual val id: String = "",
    actual val username: String = "",
    @SerialName("display_name")
    actual val displayName: String = "",
    actual val email: String = "",
    @SerialName("phone_number")
    actual val phoneNumber: String = "",
    @SerialName("photo_url")
    actual val photoUrl: String = "",
    @SerialName("provider_id")
    actual val providerId: Int = 0,
)