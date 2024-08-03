package com.yogaveda.components.yoga

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.TextTransform
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.textTransform
import com.varabyte.kobweb.silk.components.text.SpanText
import com.yogaveda.ui.YogaVedaTheme
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun YVText(modifier: Modifier = getTextModifier(), text: String, onClick: () -> Unit = {}) {
    SpanText(
        modifier = getTextModifier().then(modifier),
        text = text
    )
}

@Composable
fun YVHeadingMain(modifier: Modifier = getTextModifier(), text: String) {
    SpanText(
        modifier = getTextModifier().then(modifier),
        text = text
    )
}

fun getTextModifier() = Modifier
    .textAlign(TextAlign.Center)
    .color(YogaVedaTheme.BlackGrey.rgb)
    .fontFamily("Gotu")
    .fontSize(130.px)
    .lineHeight(1.1.em)
    .textTransform(TextTransform.Lowercase)
    .fontWeight(FontWeight.Bold)