package com.yogaveda.components.yoga

import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.yogaveda.ui.YogaVedaTheme
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px


fun getBodyTextModifier() = getTextModifier()
    .fontSize(20.px)
    .lineHeight(1.5.em)
    .fontWeight(FontWeight.Bold)
    .onClick { println("Clicked") }

fun getTitleTextModifier() = getTextModifier()
    .fontSize(50.px)
    .lineHeight(1.2.em)
    .fontWeight(FontWeight.Bold)
    .onClick { println("Clicked") }

fun getHeadingTextModifier() = getTextModifier()
    .fontSize(30.px)
    .lineHeight(1.2.em)
    .fontWeight(FontWeight.Bold)
    .onClick { println("Clicked") }

fun getHeroTextModifier() = getTextModifier()
    .fontSize(50.px)
    .lineHeight(1.2.em)
    .fontWeight(FontWeight.Bold)
    .onClick { println("Clicked") }

fun getCursiveTextModifier() = getTextModifier()
    .fontSize(30.px)
    .lineHeight(1.2.em)
    .fontWeight(FontWeight.Bold)
    .onClick { println("Clicked") }

fun getTextModifier() = Modifier
    .textAlign(TextAlign.Center)
    .color(YogaVedaTheme.BlackGrey.rgb)
    .fontFamily("Gotu")
    .fontSize(130.px)
    .lineHeight(1.1.em)
    .fontWeight(FontWeight.Normal)