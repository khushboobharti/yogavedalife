package com.yogaveda.components.yoga

import androidx.compose.runtime.Composable
import androidx.compose.web.events.SyntheticMouseEvent
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.Content
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.TransitionProperty
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.toModifier
import com.yogaveda.ui.YogaVedaTheme
import com.yogaveda.util.Id
import com.yogaveda.util.noBorder
import kotlinx.serialization.json.JsonNull.content
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.w3c.dom.events.Event



val YVButtonStyle by ComponentStyle {
    cssRule("") {
        Modifier
            .transition(CSSTransition(property = TransitionProperty.All, duration = 300.ms))
            .styleModifier {
                property("background-color", YogaVedaTheme.OrangeFaded.hex)
            }
    }
    cssRule(":hover") {
        Modifier
            .transition(CSSTransition(property = TransitionProperty.All, duration = 300.ms))
            .styleModifier {
                property("background-color", YogaVedaTheme.Orange.hex)
            }
    }

    cssRule(" > span") {
        Modifier
            .transition(CSSTransition(property = TransitionProperty.All, duration = 300.ms))
            .styleModifier {
                property("color", YogaVedaTheme.Orange.hex)
            }
    }
    cssRule(":hover > span") {
        Modifier
            .transition(CSSTransition(property = TransitionProperty.All, duration = 300.ms))
            .styleModifier {
                property("color", YogaVedaTheme.White.hex)
            }
    }
}

@Composable
fun YVButton(modifier: Modifier = YVButtonStyle.toModifier(), onClick: (event: SyntheticMouseEvent) -> Unit = {}, content: @Composable () -> Unit) {
    Button(
        attrs = modifier
            .onClick { event ->
                onClick(event)
            }
            .height(54.px)
            .margin(bottom = 130.px)
            .borderRadius(100.px)
            .padding(leftRight = 50.px)
            .noBorder()
            .cursor(Cursor.Pointer)
            .toAttrs()
    ) {
        content()
    }
}


