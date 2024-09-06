package com.yogaveda.styles.modifiers

import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.yogaveda.ui.YogaVedaTheme
import com.yogaveda.util.noBorder
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px

fun getButtonModifier() = Modifier
    .padding(leftRight = 30.px, topBottom = 15.px)
    .margin(bottom = 130.px)
    .borderRadius(30.px)
    .noBorder()
    .cursor(Cursor.Pointer)

val YVButtonStyle by ComponentStyle {
    cssRule("") {
        Modifier
            .transition(CSSTransition(property = TransitionProperty.All, duration = 300.ms))
            .styleModifier {
                property("background-color", YogaVedaTheme.Colors.OrangeFaded.hex)
            }
    }
    cssRule(":hover") {
        Modifier
            .transition(CSSTransition(property = TransitionProperty.All, duration = 300.ms))
            .styleModifier {
                property("background-color", YogaVedaTheme.Colors.Orange.hex)
            }
    }

    cssRule(" > span") {
        Modifier
            .transition(CSSTransition(property = TransitionProperty.All, duration = 300.ms))
            .styleModifier {
                property("color", YogaVedaTheme.Colors.Orange.hex)
            }
    }
    cssRule(":hover > span") {
        Modifier
            .transition(CSSTransition(property = TransitionProperty.All, duration = 300.ms))
            .styleModifier {
                property("color", YogaVedaTheme.Colors.White.hex)
            }
    }
}