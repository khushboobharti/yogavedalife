package com.yogaveda.components.yoga

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AlignContent
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignContent
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.shapes.Circle
import com.varabyte.kobweb.silk.theme.shapes.clip
import com.yogaveda.models.Testimonial
import com.yogaveda.ui.YogaVedaTheme
import kotlinx.coroutines.FlowPreview
import org.jetbrains.compose.web.css.px



@Composable
fun Testimonial(
    testimonial: Testimonial
) {
    Box(
        modifier = Modifier
            .padding(topBottom = 24.px)
            .backgroundColor(YogaVedaTheme.Colors.White.rgb)
            .margin(right = 24.px)
    ) {
        Column(
            modifier = Modifier
                .alignContent(AlignContent.Center)
        ) {
            Row() {
                Image(
                    modifier = Modifier.margin(leftRight = 4.px)
                        .clip(Circle()),
                    src =  testimonial.image,
                    alt = "Profile Image",
                    width = 32,
                    height = 32
                )
                Column() {
                    SpanText(
                        text = "Testimonial"
                    )
                }
            }
            Row() {

            }
        }
    }
}

val testimonial = Testimonial(
    id = "",
    title = "More than just yoga",
    rating = 4.5f,
    testimonialShort = "Khushboo's support during the pregnancy has been more than just of a teacher.",
    testimonial = "I must say that the Garbhasanskar classes from Khushboo were so much more than what I had expected. \nKhushboo's support during the pregnancy has been more than just of a teacher.",
    name = "Rahul Thakur",
    image = "favicon.ico",
    createdAt = "",
    updatedAt = ""
)