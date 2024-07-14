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