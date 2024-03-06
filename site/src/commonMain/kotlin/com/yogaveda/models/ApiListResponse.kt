package com.yogaveda.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class ApiListResponse {
    @Serializable
    @SerialName("idle")
    object Idle : ApiListResponse()

    @Serializable
    @SerialName("success")
    data class Success(val data: List<PostWithoutDetails>) : ApiListResponse()

    @Serializable
    @SerialName("error")
    data class Error(val message: String) : ApiListResponse()
}