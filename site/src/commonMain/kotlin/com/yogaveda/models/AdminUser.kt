@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.yogaveda.models

expect class AdminUser {
    val id: String
    val username: String
    val password: String
}

expect class AdminUserWithoutPassword {
    val id: String
    val username: String
}