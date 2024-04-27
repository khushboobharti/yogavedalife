package com.yogaveda.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.yogaveda.components.CategoryNavigationItems
import com.yogaveda.components.NavigationItems
import com.yogaveda.components.OverflowSidePanel
import com.yogaveda.sections.HeaderSection

@Page
@Composable
fun HomePage() {
    val breakpoint = rememberBreakpoint()
    var overflowMenuOpened by remember { mutableStateOf(false) }
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(overflowMenuOpened) {
            OverflowSidePanel(
                onMenuClose = { overflowMenuOpened = false },
                content = { CategoryNavigationItems(vertical = true) }
            )
        }
        HeaderSection(
            breakpoint = breakpoint,
            selectedCategory = null,
            onMenuOpen = { overflowMenuOpened = true }
        )
    }
}
