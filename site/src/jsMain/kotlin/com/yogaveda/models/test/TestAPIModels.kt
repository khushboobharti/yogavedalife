package com.yogaveda.models.test

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest(val email: String, val password: String, val userType: String)

@Serializable
data class RegistrationResponse(val isSuccess: Boolean, val data: RegistrationData, val statusCode: StatusCodeData)

@Serializable
data class RegistrationData(val email: String, val id: String)

@Serializable
data class StatusCodeData(val value: Int, val description: String)