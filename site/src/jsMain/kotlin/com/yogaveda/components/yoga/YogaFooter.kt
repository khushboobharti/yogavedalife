package com.yogaveda.components.yoga

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.layout.HorizontalDivider
import com.varabyte.kobweb.silk.components.text.SpanText
import com.yogaveda.styles.modifiers.getTextModifier
import com.yogaveda.ui.YogaVedaTheme
import com.yogaveda.util.Constants.FONT_FAMILY
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.A

@Composable
fun YogaFooter() {
    HorizontalDivider()
    Row(
        modifier = Modifier
            .fillMaxWidth(percent = 90.percent),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(topBottom = 50.px),
            contentAlignment = Alignment.TopStart
        ) {
            getFooterMenu()
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(topBottom = 50.px),
            contentAlignment = Alignment.TopEnd
        ) {
            Row {
                SpanText(
                    modifier = getTextModifier()
                        .fontSize(14.px),
                    text = "Copyright © 2024 • "
                )
                SpanText(
                    modifier = getTextModifier()
                        .fontFamily(FONT_FAMILY)
                        .fontSize(14.px)
                        .color(YogaVedaTheme.Colors.Orange.rgb),
                    text = "YogaVeda Life"
                )
            }
        }
    }
}

@Composable
fun getFooterMenu() {

    val menuItems = listOf<Pair<String, String>>(
        "Terms & Conditions" to "",
        "Privacy Policy" to "",
        "FAQ" to "",
        "Contact" to ""
    )
    Row(
        modifier = Modifier
    ) {
        menuItems.forEach { menuItem ->
            A(
                href = menuItem.second,
                attrs = Modifier
                    .textDecorationLine(TextDecorationLine.None)
                    .toAttrs(),
            ) {
                SpanText(
                    modifier = getTextModifier()
                        .fontFamily(FONT_FAMILY)
                        .fontSize(14.px)
                        .padding(right = 12.px),
                    text = menuItem.first
                )
            }
        }
    }
}