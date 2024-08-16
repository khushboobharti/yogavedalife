package com.yogaveda.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.layout.HorizontalDivider
import com.varabyte.kobweb.silk.components.text.SpanText
import com.yogaveda.components.yoga.getTextModifier
import com.yogaveda.ui.Theme
import com.yogaveda.ui.YogaVedaTheme
import com.yogaveda.util.Constants.FONT_FAMILY
import org.jetbrains.compose.web.css.px

@Composable
fun MainFooterSection() {
    HorizontalDivider()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(topBottom = 50.px),
        contentAlignment = Alignment.Center
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
                    .color(YogaVedaTheme.Orange.rgb),
                text = "YogaVeda Life"
            )
        }
    }
}

@Composable
fun AdminFooterSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(topBottom = 50.px)
            .backgroundColor(Theme.Secondary.rgb),
        contentAlignment = Alignment.Center
    ) {
        Row {
            SpanText(
                modifier = Modifier
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .color(Colors.White),
                text = "Copyright © 2024 • "
            )
            SpanText(
                modifier = Modifier
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .color(Theme.Primary.rgb),
                text = "RCode"
            )
        }
    }
}