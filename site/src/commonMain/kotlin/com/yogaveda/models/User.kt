@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.yogaveda.models

expect class User {
    val id: String
    val displayName: String
    val email: String
    val phoneNumber: String
    val photoURL: String
    val providerId: String
    val accessToken: String
    val refreshToken: String
}
