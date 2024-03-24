@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.yogaveda.models

expect sealed class ApiListResponse {
    object Idle
    class Success
    class Error
}