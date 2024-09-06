package com.yogaveda.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Testimonial(
    @SerialName("_id")
    val id: String,
    val name: String,
    val image: String,
    val title: String,
    @SerialName("testimonial_short")
    val testimonialShort: String,
    val testimonial: String,
    val rating: Float = 0.0f,
    val createdAt: String,
    val updatedAt: String
)
