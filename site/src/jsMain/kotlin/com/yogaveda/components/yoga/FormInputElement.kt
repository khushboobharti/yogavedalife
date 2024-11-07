package com.yogaveda.components.yoga

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.toAttrs
import com.yogaveda.styles.modifiers.getInputTextModifier
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text

@Composable
fun formInputElement(id: String, placeHolder: String, title: String, value: String) {
    Column {
        Text(title)
        Input(
            type = InputType.Text,
            attrs = getInputTextModifier()
                .id(id)
                .toAttrs {
                    attr("placeholder", placeHolder)
                    attr("value", value)
                }
        )
    }
}