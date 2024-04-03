@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.yogaveda.models

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

@Serializable(with = ApiListResponseSerializer::class)
actual sealed class ApiListResponse {
    @Serializable
    @SerialName("idle")
    actual object Idle : ApiListResponse()

    @Serializable
    @SerialName("success")
    actual data class Success(val data: List<PostWithoutDetails>) : ApiListResponse()

    @Serializable
    @SerialName("error")
    actual data class Error(val message: String) : ApiListResponse()
}

@Serializable(with = ApiResponseSerializer::class)
actual sealed class ApiResponse {
    @Serializable
    @SerialName("idle")
    actual object Idle : ApiResponse()

    @Serializable
    @SerialName("success")
    actual data class Success(val data: Post) : ApiResponse()

    @Serializable
    @SerialName("error")
    actual data class Error(val message: String) : ApiResponse()
}

object ApiListResponseSerializer: JsonContentPolymorphicSerializer<ApiListResponse>(ApiListResponse::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<ApiListResponse> = when{

        "data" in element.jsonObject -> ApiListResponse.Success.serializer()
        "message" in element.jsonObject -> ApiListResponse.Error.serializer()
        else -> ApiListResponse.Idle.serializer()
    }
}

object ApiResponseSerializer: JsonContentPolymorphicSerializer<ApiResponse>(ApiResponse::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<ApiResponse> = when{

        "data" in element.jsonObject -> ApiResponse.Success.serializer()
        "message" in element.jsonObject -> ApiResponse.Error.serializer()
        else -> ApiResponse.Idle.serializer()
    }
}