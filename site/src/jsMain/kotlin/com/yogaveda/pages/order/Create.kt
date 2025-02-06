package com.yogaveda.pages.order

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.yogaveda.styles.modifiers.YVButtonStyle
import com.yogaveda.styles.modifiers.getButtonModifier
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button


@Page(routeOverride = "create")
@Composable
fun createOrderPage() {

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            attrs = YVButtonStyle.toModifier()
                .then(getButtonModifier())
                .onClick {
                    scope.launch {
                        initiatePayment(5000, "INR")
                    }
                }
                .toAttrs()
        ) {
            SpanText(
                modifier = Modifier
                    .fontFamily("Archivo", "Arial")
                    .fontSize(14.px)
                    .fontWeight(FontWeight.SemiBold),
                text = "Make Payment"
            )
        }
    }
}