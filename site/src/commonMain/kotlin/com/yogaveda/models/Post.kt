@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.yogaveda.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
expect class Post {
    @SerialName("_id")
    val _id: String
    val author: String
    val date: Long
    val title: String
    val subtitle: String
    val thumbnail: String
    val content: String
    val category: Category
    val popular: Boolean
    val main: Boolean
    val sponsored: Boolean
}

@Serializable
expect class PostWithoutDetails {
    @SerialName("_id")
    val _id: String
    val author: String
    val date: Long
    val title: String
    val subtitle: String
    val thumbnail: String
    val category: Category
}