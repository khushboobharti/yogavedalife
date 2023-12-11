package com.yogaveda.pages.admin

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.yogaveda.components.AdminPageLayout
import com.yogaveda.util.isUserLoggedIn

@Page
@Composable
fun CreatePage() {
    isUserLoggedIn {
        CreateScreen()
    }
}


@Composable
fun CreateScreen() {
    AdminPageLayout {

    }
}