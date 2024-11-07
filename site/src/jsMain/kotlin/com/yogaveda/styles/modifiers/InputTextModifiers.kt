package com.yogaveda.styles.modifiers

import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.yogaveda.ui.Theme
import com.yogaveda.ui.YogaVedaTheme
import com.yogaveda.util.Constants.FONT_FAMILY
import com.yogaveda.util.noBorder
import org.jetbrains.compose.web.css.px


fun getInputTextModifier() = Modifier
    .fillMaxWidth()
    .height(54.px)
    //.margin(topBottom = 12.px)
    .padding(leftRight = 20.px)
    .backgroundColor(YogaVedaTheme.Colors.White.rgb)
    .borderRadius(r = 4.px)
    .noBorder()
    .fontFamily(FONT_FAMILY)
    .fontSize(16.px)