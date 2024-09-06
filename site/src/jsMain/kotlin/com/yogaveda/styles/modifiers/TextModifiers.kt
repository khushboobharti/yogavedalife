package com.yogaveda.styles.modifiers

import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.TextTransform
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.letterSpacing
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.textTransform
import com.yogaveda.ui.YogaVedaTheme
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px


fun getTitleTextModifier() = getTextModifier()
    .fontSize(50.px)
    .lineHeight(1.2.em)
    .fontWeight(FontWeight.Bold)
    .onClick { println("Clicked") }



fun getHeroTextModifier() = getTextModifier()
    .fontSize(50.px)
    .lineHeight(1.2.em)
    .fontWeight(FontWeight.Bold)
    .onClick { println("Clicked") }


fun getTextModifier() = Modifier
    .textAlign(TextAlign.Center)
    .color(YogaVedaTheme.Colors.BlackGrey.rgb)
    .fontFamily(YogaVedaTheme.Fonts.Gotu.family)
    .fontSize(130.px)
    .lineHeight(1.1.em)
    .fontWeight(FontWeight.Normal)

fun getBaseTextModifier() = getTextModifier()
    .fontFamily("League Script", "Arial")
    .fontSize(80.px)
    .color(YogaVedaTheme.Colors.Orange.rgb)
    .fontWeight(FontWeight.Light)
    .textTransform(TextTransform.Lowercase)

fun getCursiveTextModifier() = Modifier
    .fontFamily(YogaVedaTheme.Fonts.LeagueScript.family, YogaVedaTheme.Fonts.Arial.family)
    .fontSize(80.px)
    .color(YogaVedaTheme.Colors.Orange.rgb)
    .fontWeight(FontWeight.Light)
    .textTransform(TextTransform.Lowercase)

fun getHeadingTextModifier() = Modifier
    .textAlign(TextAlign.Center)
    .fontFamily(YogaVedaTheme.Fonts.Gotu.family)
    .fontSize(50.px)
    .color(YogaVedaTheme.Colors.BlackGrey.rgb)
    .fontWeight(FontWeight.Light)

fun getBodyTextModifier() = getTextModifier()
    .textAlign(TextAlign.Left)
    .fontSize(16.px)
    .fontFamily(YogaVedaTheme.Fonts.Archivo.family)
    .letterSpacing(0.3.px)
    .lineHeight(22.px)
    .fontWeight(400)
    .flexWrap(FlexWrap.Wrap)

fun getLinkModifier() = Modifier
    .color(YogaVedaTheme.Colors.Orange.rgb)
    .fontWeight(FontWeight.Bold)