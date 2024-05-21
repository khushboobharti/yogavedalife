@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.yogaveda.models

import com.yogaveda.CategoryCommon
import com.yogaveda.ui.Theme
import kotlinx.serialization.Serializable

@Serializable
enum class Category(override val color: String): CategoryCommon {
    Technology(color = Theme.Green.hex),
    Programming(color = Theme.Yellow.hex),
    Design(color = Theme.Purple.hex)
}