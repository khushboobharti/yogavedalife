package com.yogaveda.styles.templates

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.core.rememberPageContext
import com.yogaveda.components.yoga.YogaFooter
import com.yogaveda.components.yoga.YogaHeader
import com.yogaveda.ui.YogaVedaTheme

/**
 * The main template encapsulates the entire website layout.
 * It contains the header, footer, and the content area.
 * The content area is where the main content of each page will be displayed.
 * The header and footer are common across all pages.
 *
 * @param content The content to be displayed in the main area of the template.
 */
@Composable
fun YogaMainTemplate(content: @Composable () -> Unit) {

    val context = rememberPageContext()


    Column(
        modifier = Modifier.fillMaxSize()
            .backgroundColor(YogaVedaTheme.Colors.BackgroundWhitish.rgb),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        content()
        YogaFooter()
    }

    Box (
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        YogaHeader()
    }

}