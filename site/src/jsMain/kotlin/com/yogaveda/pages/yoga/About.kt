package com.yogaveda.pages.yoga

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.AlignContent
import com.varabyte.kobweb.compose.css.BackgroundImage
import com.varabyte.kobweb.compose.css.BackgroundPosition
import com.varabyte.kobweb.compose.css.BackgroundSize
import com.varabyte.kobweb.compose.css.CSSPosition
import com.varabyte.kobweb.compose.css.Visibility
import com.varabyte.kobweb.compose.css.functions.url
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignContent
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.backgroundImage
import com.varabyte.kobweb.compose.ui.modifiers.backgroundPosition
import com.varabyte.kobweb.compose.ui.modifiers.backgroundSize
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.visibility
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.yogaveda.styles.modifiers.getBodyTextModifier
import com.yogaveda.styles.modifiers.getHeadingTextModifier
import com.yogaveda.ui.YogaVedaTheme
import com.yogaveda.util.Res
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

@Page(routeOverride = "about")
@Composable
fun AboutPage() {
    AboutKhushbooBharti()
}

@Composable
fun AboutKhushbooBharti() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .margin(bottom = 80.px)
            .padding(leftRight = 24.px)
            .backgroundColor(YogaVedaTheme.Colors.OrangeFaded.rgb),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(40.percent)
                .fillMaxHeight()
                .weight(0.5)
                .margin(right = 24.px)
                //.backgroundImage(url("https://studioyogaveda.in/wp-content/uploads/elementor/thumbs/khush_profile_1-e1705595822395-qii0geykw4a1dh2g93ljrfawa75825iyt7asmshehw.jpg"))
                .backgroundImage(backgroundImage = BackgroundImage.of(url(Res.Image.khush_profile_1)))
                .backgroundSize(BackgroundSize.Cover)
                .backgroundPosition(BackgroundPosition.of(CSSPosition.Top))
        ) {
            Image(
                modifier = Modifier
                    .size(150.px)
                    .margin(bottom = 50.px)
                    .minHeight(480.px)
                    .margin(right = 24.px)
                    .visibility(Visibility.Hidden)
                    .fillMaxWidth(40.percent),
                src = "https://studioyogaveda.in/wp-content/uploads/elementor/thumbs/khush_profile_1-e1705595822395-qii0geykw4a1dh2g93ljrfawa75825iyt7asmshehw.jpg",
                alt = "Laugh Image"
            )
            Div { }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(40.percent)
                .fillMaxHeight()
                .weight(0.5)
                .padding(topBottom = 24.px)
                .margin(right = 24.px)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .alignContent(AlignContent.Center)
                    .margin(right = 24.px, left = 24.px)
            ) {
                SpanText(
                    modifier = getHeadingTextModifier()
                        .fontSize(36.px)
                        .fontWeight(400),
                    text = "Khushboo Bharti"
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .margin(bottom = 12.px),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SpanText(
                        modifier = getBodyTextModifier()
                            .color(YogaVedaTheme.Colors.Orange.rgb)
                            .fontSize(18.px)
                            .fontWeight(400),
                        text = "Founder"
                    )
                    SpanText(
                        modifier = getBodyTextModifier()
                            .fontSize(18.px)
                            .fontWeight(400),
                        text = " | "
                    )
                    SpanText(
                        modifier = getBodyTextModifier()
                            .fontSize(18.px)
                            .fontWeight(400)
                            .color(YogaVedaTheme.Colors.Orange.rgb),
                        text = "Yoga Teacher"
                    )
                }
                SpanText(
                    modifier = getLocalBodyModifier(),
                    text = "हरी ॐ \n" )
                SpanText(
                    modifier = getLocalBodyModifier()
                        .margin(bottom = 12.px),
                    text = "Khushboo Bharti is an ex- Fashion Designer and a Yoga Practitioner who turned to teaching Yoga."
                )
                /*SpanText(
                    modifier = getLocalBodyModifier(),
                    text = "She believes that as a Society we can flourish only when we, educate and take adequate care of Physical, Mental and Spiritual Health of ourselves.\n"
                )*/
                SpanText(
                    modifier = getLocalBodyModifier()
                        .fontWeight(600),
                    text = "Her formal trainings are from – \n"
                )
                SpanText(
                    modifier = getLocalBodyModifier()
                        .fontWeight(250),
                    text = "    - RYT 200hrs Yoga Alliance USA & Vinyasa Yoga Ashram Rishikesh\n"
                )
                SpanText(
                    modifier = getLocalBodyModifier()
                        .fontWeight(250),
                    text = "    - Yoga Therapy, YogaVidya Gurukul Nashik\n"
                )
                SpanText(
                    modifier = getLocalBodyModifier()
                        .fontWeight(250),
                    text = "    - Garbhsansakaar, Prenatal & Postnatal Yoga AYG Academy Mumbai\n"
                )
                SpanText(
                    modifier = getLocalBodyModifier()
                        .fontWeight(250),
                    text = "    - She is also a CHILDBIRTH EDUCATOR practicing under Lamaze ChildBirth Philosophy and have been formally trained under Dr. Vijaya from Sanctum Birth, Hyderabad.\n"
                )

                /*SpanText(
                    modifier = getLocalBodyModifier(),
                    text = ""
                )
                SpanText(
                    modifier = getLocalBodyModifier(),
                    text = "It’s been 20 years of her practicing Yoga, and she is formally teaching since 2021, sharing this ancient gift of yoga with people Pan India and Globally.\n" +
                            "\n" +
                            "Her classes are full of energy and different themes and gives new experiences every time. The classes are a combination of asanas, meditation, pranayama, kriyas, yogic philosophy and spirituality.\n" +
                            "\n" +
                            "Her teachings are based on traditional methods of Hatha, Ashtanga and lyengar.\n" +
                            "\n" +
                            "<b>Yoga Studies:</b>\n" +
                            "\n" +
                            "She learned yoga at a very early age and became a regular practitioner of yoga since the age of 15.\n" +
                            "\n" +
                            "Her formal trainings are from –\n" +
                            "\n" +
                            "RYT 200hrs Yoga Alliance USA & Vinyasa Yoga Ashram Rishikesh\n" +
                            "\n" +
                            "Yoga Therapy, YogaVidya Gurukul Nashik\n" +
                            "\n" +
                            "Garbhsansakaar, Prenatal & Postnatal Yoga AYG Academy Mumbai\n" +
                            "\n" +
                            "She is also a CHILDBIRTH EDUCATOR practicing under Lamaze ChildBirth Philosophy and have been formally trained under Dr. Vijaya from Sanctum Birth, Hyderabad.\n" +
                            "\n"
                )
                SpanText(
                    modifier = getBodyTextModifier()
                        .margin(top = 16.px)
                        .fontSize(24.px)
                        .fontWeight(700)
                        .lineHeight(30.px)
                        .color(YogaVedaTheme.Colors.Orange.rgb),
                    text = "Khushboo Bharti"
                )*/
            }
        }
    }
}

fun getLocalBodyModifier() = getBodyTextModifier()
    .fontSize(20.px)
    .fontWeight(400)
    .lineHeight(30.px)
    .flexWrap(FlexWrap.Nowrap)
