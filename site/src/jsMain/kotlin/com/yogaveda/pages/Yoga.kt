package com.yogaveda.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.TextTransform
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.letterSpacing
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.scale
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.textTransform
import com.varabyte.kobweb.compose.ui.modifiers.whiteSpace
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaPhone
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.yogaveda.components.yoga.YVButtonStyle
import com.yogaveda.components.yoga.getTextModifier
import com.yogaveda.sections.MainFooterSection
import com.yogaveda.ui.YogaVedaTheme
import com.yogaveda.util.Res
import com.yogaveda.util.noBorder
import kotlinx.browser.window
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button

@Page
@Composable
fun YogaPage() {

    val context = rememberPageContext()
    val scope = rememberCoroutineScope()
    val breakpoint = rememberBreakpoint()


    val windowHeight = remember { mutableStateOf(window.innerHeight) }  //remember

    var textScale = remember { mutableStateOf(1f) }

    /*// TODO: Replace the following with your own content
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("THIS IS A YOGA PAGE")
    }*/

    LaunchedEffect(Unit) {

        window.addEventListener("resize", {
            windowHeight.value = window.innerHeight
        })

        window.addEventListener("scroll", { scrollEvent ->
            //windowHeight.value = windowHeight.value
            var scaleValue = when {
                (1f - (window.scrollY.toFloat() / windowHeight.value)) < 0.7f -> 0.7f
                else -> (1f - (window.scrollY.toFloat() / windowHeight.value))
            }
            textScale.value = scaleValue
        })
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .backgroundColor(YogaVedaTheme.BackgroundWhitish.rgb),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeroSection(textScale, windowHeight)
        Box(
            modifier = Modifier
                .margin(bottom = 90.px),
            contentAlignment = Alignment.Center
        ) {
            SpanText(
                modifier = getTextModifier().then(
                    Modifier
                        .fontFamily("League Script", "Arial")
                        .fontSize(80.px)
                        .color(YogaVedaTheme.Orange.rgb)
                        .fontWeight(FontWeight.Light)
                        .textTransform(TextTransform.Lowercase)
                ),
                text = "A Journey Withing"
            )
        }
        SpanText(
            modifier = getTextModifier().then(
                Modifier
                    //.fontFamily("League Script", "Arial")
                    .fontSize(50.px)
                    .margin(bottom = 90.px)
                    .color(YogaVedaTheme.BlackGrey.rgb)
                    .fontWeight(FontWeight.Light)
            ),
            text = "A holistic approach to Yoga, Well Being and Child Birth"
        )
        SimpleGrid(
            modifier = Modifier.fillMaxWidth()
                .fillMaxWidth(80.percent),
            numColumns = numColumns(base = 1, sm = 1, md = 3, lg = 3)
        ) {
            Box (
                modifier = Modifier
                    .padding(leftRight = 12.px)
            ) {
                Column(
                    modifier = Modifier
                        .margin(bottom = 50.px)
                        .padding(48.px),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .size(150.px)
                            .margin(bottom = 50.px),
                        src = Res.Icon.body,
                        alt = "Laugh Image"
                    )
                    SpanText(
                        modifier = getTextModifier().then(
                            Modifier
                                .fontSize(24.px)
                                .whiteSpace(WhiteSpace.NoWrap)
                                .margin(topBottom = 15.px)
                        ),
                        text = "Pregnancy and beyond..."
                    )
                    SpanText(
                        modifier = getTextModifier().then(
                            Modifier
                                .fontSize(16.px)
                                .fontFamily("Archivo")
                                .textAlign(TextAlign.Center)
                                .letterSpacing(0.3.px)
                                .fontWeight(400)
                                .flexWrap(FlexWrap.Wrap)
                        ),
                        text = "An all-round approach to Pregnancy the includes Prenatal Yoga, Garbhasanskar, Nutrition support, Child Birth education, Lactation, Infant Care, Postnatal Yoga and more."
                    )
                }
            }
            Box (
                modifier = Modifier
                    .padding(leftRight = 12.px)
            ) {
                Column(
                    modifier = Modifier
                        .margin(bottom = 50.px)
                        .padding(48.px),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .size(150.px)
                            .margin(bottom = 50.px),
                        src = Res.Icon.health,
                        alt = "Laugh Image"
                    )
                    SpanText(
                        modifier = getTextModifier().then(
                            Modifier
                                .fontSize(24.px)
                                .margin(topBottom = 15.px)
                        ),
                        text = "General Fitness"
                    )
                    SpanText(
                        modifier = getTextModifier().then(
                            Modifier
                                .fontSize(16.px)
                                .fontFamily("Archivo")
                                .flexWrap(FlexWrap.Wrap)
                        ),
                        text = "The body is of utmost importance for our holistic health as it is here that the mental and spiritual realms reside. Yogaveda classes are designed to nurture your physical and spiritual wellbeing."
                    )
                }
            }
            Box (
                modifier = Modifier
                    .padding(leftRight = 12.px)
            ) {
                Column(
                    modifier = Modifier
                        .margin(bottom = 50.px)
                        .padding(48.px),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .size(150.px)
                            .margin(bottom = 50.px),
                        src = Res.Icon.stress,
                        alt = "Laugh Image"
                    )
                    SpanText(
                        modifier = getTextModifier().then(
                            Modifier
                                .fontSize(24.px)
                                .margin(topBottom = 15.px)
                        ),
                        text = "Women Wellness"
                    )
                    SpanText(
                        modifier = getTextModifier().then(
                            Modifier
                                .fontSize(16.px)
                                .fontFamily("Archivo")
                                .flexWrap(FlexWrap.Wrap)
                        ),
                        text = "The body is of utmost importance for our holistic health as it is here that the mental and spiritual realms reside."
                    )
                }
            }
        }
        AboutKhushbooBharti()
        SpanText(
            modifier = getTextModifier()
                .fontSize(50.px)
                .margin(bottom = 90.px)
                .color(YogaVedaTheme.BlackGrey.rgb)
                .fontWeight(FontWeight.Light),
            text = "Find the right class for you"
        )
        YogaOptions()
        BottomContactSection()
        MainFooterSection()
    }
}


@Composable
fun HeroSection(textScale: MutableState<Float>, windowHeight: MutableState<Int>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .height(windowHeight.value.px)
                .fillMaxWidth(80.percent),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SpanText(
                text = "Sat",
                modifier = getTextModifier().then(
                    Modifier
                        .scale(textScale.value)
                        .textTransform(TextTransform.Lowercase)
                )
            )
            SpanText(
                text = "Chitt",
                modifier = getTextModifier().then(
                    Modifier
                        .color(YogaVedaTheme.OrangeFaded.rgb)
                        .scale(textScale.value)
                        .textTransform(TextTransform.Lowercase)
                )
            )
            SpanText(
                text = "Anand",
                modifier = getTextModifier().then(
                    Modifier
                        .scale(textScale.value)
                        .textTransform(TextTransform.Lowercase)
                )
            )
        }
        Button(
            attrs = YVButtonStyle.toModifier()
                .onClick {}
                .height(54.px)
                .margin(bottom = 130.px)
                .borderRadius(100.px)
                .padding(leftRight = 50.px)
                .noBorder()
                .cursor(Cursor.Pointer)
                .toAttrs()
        ) {
            SpanText(
                modifier = Modifier
                    .fontFamily("Archivo", "Arial")
                    .fontSize(14.px)
                    .fontWeight(FontWeight.SemiBold),
                text = "Join Our Classes"
            )
        }
    }
}

@Composable
fun AboutKhushbooBharti() {
    Row(
        modifier = Modifier
            .fillMaxWidth(80.percent)
            .margin(bottom = 80.px),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(150.px)
                .margin(bottom = 50.px)
                .minHeight(480.px)
                .margin(right = 24.px)
                .fillMaxWidth(40.percent),
            src = "https://studioyogaveda.in/wp-content/uploads/elementor/thumbs/khush_profile_1-e1705595822395-qii0geykw4a1dh2g93ljrfawa75825iyt7asmshehw.jpg",
            alt = "Laugh Image"
        )
        SpanText(
            modifier = getTextModifier().then(
                Modifier
                    .fontSize(16.px)
                    .fontFamily("Archivo")
                    .flexWrap(FlexWrap.Wrap)
                    .fillMaxHeight(100.percent)
                    .align(Alignment.CenterVertically)
            ),
            text = "Hari Om\n" +
                    "\n" +
                    "Khushboo Bharti is a yoga teacher based out of Gurgaon. She is an ex- Fashion Designer and a Yoga Practitioner who turned to teaching Yoga to. Through Yoga fulfil her Vision of a better planet. She found her purpose in teaching concepts of Yoga at the seed level through the Science of Garbhsanskaar and Prenatal Yoga.\n" +
                    "\n" +
                    "She believes that as a Society we can flourish only when we, educate and take adequate care of Physical, Mental and Spiritual Health of would be Mothers.\n" +
                    "\n" +
                    "It’s been 20 years of her practicing Yoga, and she is formally teaching since 2021, sharing this ancient gift of yoga with people Pan India and Globally.\n" +
                    "\n" +
                    "Her classes are full of energy and different themes and gives new experiences every time. The classes are a combination of asanas, meditation, pranayama, kriyas, yogic philosophy and spirituality.\n" +
                    "\n" +
                    "Her teachings are based on traditional methods of Hatha, Ashtanga and lyengar.\n" +
                    "\n" +
                    "Yoga Studies:\n" +
                    "\n" +
                    "She learned yoga at a very early age and became a regular practitioner yoga since the age of 15.\n" +
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
    }
}


@Composable
fun YogaOptions() {
    SimpleGrid(
        modifier = Modifier.fillMaxWidth(80.percent),
        numColumns = numColumns(base = 1, sm = 2, md = 4, lg = 4)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(10.px)
                .margin(leftRight = 20.px)
                .background(YogaVedaTheme.White.rgb)
                .borderRadius(r = 110.px)
                .noBorder(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(100.px),
                    src = Res.Icon.icon2,
                    alt = "Laugh Image"
                )
                SpanText(
                    modifier = getTextModifier().then(
                        Modifier
                            .fontSize(24.px)
                            .margin(topBottom = 15.px)
                    ),
                    text = "General Fitness"
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(10.px)
                .margin(leftRight = 20.px)
                .background(YogaVedaTheme.White.rgb)
                .borderRadius(r = 110.px)
                .noBorder(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    modifier = Modifier
                        .size(100.px),
                    src = Res.Icon.icon3,
                    alt = "Laugh Image"
                )
                SpanText(
                    modifier = getTextModifier().then(
                        Modifier
                            .fontSize(24.px)
                            .margin(topBottom = 15.px)
                    ),
                    text = "Prenatal Yoga"
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(10.px)
                .margin(leftRight = 20.px)
                .background(YogaVedaTheme.White.rgb)
                .borderRadius(r = 110.px)
                .noBorder(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    modifier = Modifier
                        .size(100.px),
                    src = Res.Icon.icon1,
                    alt = "Laugh Image"
                )
                SpanText(
                    modifier = getTextModifier().then(
                        Modifier
                            .fontSize(24.px)
                            .margin(topBottom = 15.px)
                    ),
                    text = "Postnatal Yoga"
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(10.px)
                .margin(leftRight = 20.px)
                .background(YogaVedaTheme.White.rgb)
                .borderRadius(r = 110.px)
                .noBorder(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    modifier = Modifier
                        .size(100.px),
                    src = Res.Icon.icon4,
                    alt = "Laugh Image"
                )
                SpanText(
                    modifier = getTextModifier().then(
                        Modifier
                            .fontSize(24.px)
                            .margin(topBottom = 15.px)
                    ),
                    text = "Childbirth"
                )
            }
        }
    }
}

@Composable
fun BottomContactSection() {
    Column(
        modifier = Modifier
            .margin(bottom = 50.px)
            .width(400.px),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpanText(
            modifier = getTextModifier().then(
                Modifier
                    .fontSize(24.px)
                    .margin(topBottom = 15.px)
                    .color(YogaVedaTheme.BlackGrey.rgb)
                    .fontWeight(FontWeight.Light)
            ),
            text = "Invest in your health.\n" +
                    "Studio Yogaveda, Gurgaon"
        )
        Button(
            attrs = Modifier
                .borderRadius(r = 30.px)
                .padding(leftRight = 30.px, topBottom = 15.px)
                //.border(1.px, LineStyle.Solid, YogaVedaTheme.Orange.rgb)
                .onClick { }
                .noBorder()
                .cursor(Cursor.Pointer)
                .background(YogaVedaTheme.White.rgb).toAttrs(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                FaPhone(
                    modifier = Modifier
                        .color(YogaVedaTheme.Orange.rgb)
                        .margin(right = 15.px)
                )
                SpanText(
                    modifier = Modifier
                        .fontFamily("Archivo", "Arial")
                        .fontSize(14.px)
                        .fontWeight(FontWeight.SemiBold)
                        .color(YogaVedaTheme.Orange.rgb),
                    text = "+(91) 9718-920-120"
                )
            }
        }
    }
}

