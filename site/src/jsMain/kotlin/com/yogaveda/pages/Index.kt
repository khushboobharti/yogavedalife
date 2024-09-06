package com.yogaveda.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.varabyte.kobweb.compose.css.BackgroundPosition
import com.varabyte.kobweb.compose.css.BackgroundSize
import com.varabyte.kobweb.compose.css.CSSPosition
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.TextTransform
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.css.functions.url
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.backgroundImage
import com.varabyte.kobweb.compose.ui.modifiers.backgroundPosition
import com.varabyte.kobweb.compose.ui.modifiers.backgroundSize
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
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
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.navigation.OpenLinkStrategy
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaPhone
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.yogaveda.components.yoga.YogaFooter
import com.yogaveda.pages.yoga.AboutKhushbooBharti
import com.yogaveda.sections.MainFooterSection
import com.yogaveda.styles.modifiers.YVButtonStyle
import com.yogaveda.styles.modifiers.getBodyTextModifier
import com.yogaveda.styles.modifiers.getButtonModifier
import com.yogaveda.styles.modifiers.getCursiveTextModifier
import com.yogaveda.styles.modifiers.getHeadingTextModifier
import com.yogaveda.styles.modifiers.getTextModifier
import com.yogaveda.ui.YogaVedaTheme
import com.yogaveda.util.Res
import com.yogaveda.util.noBorder
import kotlinx.browser.window
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
            .backgroundColor(YogaVedaTheme.Colors.BackgroundWhitish.rgb),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeroSection(context, textScale, windowHeight)
        Box(
            modifier = Modifier
                .margin(bottom = 90.px),
            contentAlignment = Alignment.Center
        ) {
            SpanText(
                modifier = getCursiveTextModifier(),
                text = "A Journey Withing"
            )
        }
        SpanText(
            modifier = getHeadingTextModifier()
                .margin(bottom = 90.px),
            text = "A holistic approach to Yoga, Well Being and Child Birth"
        )
        YogaBenefits()
        //AboutKhushbooBharti()
        SpanText(
            modifier = getTextModifier()
                .fontSize(50.px)
                .margin(bottom = 90.px)
                .color(YogaVedaTheme.Colors.BlackGrey.rgb)
                .fontWeight(FontWeight.Light),
            text = "Find the right class for you"
        )
        YogaOptions()
        AboutKhushbooBharti()
        BottomContactSection()
        YogaFooter()
        //MainFooterSection()
    }
}


@Composable
fun HeroSection(
    context: PageContext,
    textScale: MutableState<Float>,
    windowHeight: MutableState<Int>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(windowHeight.value.px)
            .backgroundImage(url("https://studioyogaveda.in/wp-content/uploads/2024/01/khush_profile_3.jpg"))
            .backgroundSize(BackgroundSize.Cover)
            .backgroundPosition(BackgroundPosition.of(CSSPosition.Center)),
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
                modifier = getTextModifier()
                    .scale(textScale.value)
                    .textTransform(TextTransform.Lowercase)
            )
            SpanText(
                text = "Chitt",
                modifier = getTextModifier()
                    .color(YogaVedaTheme.Colors.OrangeFaded.rgb)
                    .scale(textScale.value)
                    .textTransform(TextTransform.Lowercase)
            )
            SpanText(
                text = "Anand",
                modifier = getTextModifier()
                    .scale(textScale.value)
                    .textTransform(TextTransform.Lowercase)
            )
        }
        Button(
            attrs = YVButtonStyle.toModifier()
                .then(getButtonModifier())
                .onClick {
                    //context.router.navigateTo("/contact", OpenLinkStrategy.SAME_WINDOW)
                }
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
fun YogaBenefits() {
    SimpleGrid(
        modifier = Modifier.fillMaxWidth()
            .fillMaxWidth(80.percent),
        numColumns = numColumns(base = 1, sm = 1, md = 3, lg = 3)
    ) {
        Box(
            modifier = Modifier
                .padding(leftRight = 12.px)
        ) {
            Column(
                modifier = Modifier
                    .margin(bottom = 50.px)
                    .padding(48.px),
                horizontalAlignment = Alignment.Start
            ) {
                Image(
                    modifier = Modifier
                        .size(150.px)
                        .margin(bottom = 50.px),
                    src = Res.Icon.body,
                    alt = "Laugh Image"
                )
                SpanText(
                    modifier = getTextModifier()
                        .fontSize(24.px)
                        .textAlign(TextAlign.Left)
                        .whiteSpace(WhiteSpace.NoWrap)
                        .margin(topBottom = 15.px),
                    text = "Pregnancy and beyond..."
                )
                SpanText(
                    modifier = getBodyTextModifier(),
                    text = "An all-round approach to Pregnancy the includes Prenatal Yoga, Garbhasanskar, Nutrition support, Child Birth education, Lactation, Infant Care, Postnatal Yoga and more."
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(leftRight = 12.px)
        ) {
            Column(
                modifier = Modifier
                    .margin(bottom = 50.px)
                    .padding(48.px),
                horizontalAlignment = Alignment.Start
            ) {
                Image(
                    modifier = Modifier
                        .size(150.px)
                        .margin(bottom = 50.px),
                    src = Res.Icon.health,
                    alt = "Laugh Image"
                )
                SpanText(
                    modifier = getTextModifier()
                        .fontSize(24.px)
                        .textAlign(TextAlign.Left)
                        .whiteSpace(WhiteSpace.NoWrap)
                        .margin(topBottom = 15.px),
                    text = "General Fitness"
                )
                SpanText(
                    modifier = getBodyTextModifier(),
                    text = "The body is of utmost importance for our holistic health as it is here that the mental and spiritual realms reside. Yogaveda classes are designed to nurture your physical and spiritual wellbeing."
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(leftRight = 12.px)
        ) {
            Column(
                modifier = Modifier
                    .margin(bottom = 50.px)
                    .padding(48.px),
                horizontalAlignment = Alignment.Start
            ) {
                Image(
                    modifier = Modifier
                        .size(150.px)
                        .margin(bottom = 50.px),
                    src = Res.Icon.stress,
                    alt = "Laugh Image"
                )
                SpanText(
                    modifier = getTextModifier()
                        .fontSize(24.px)
                        .textAlign(TextAlign.Left)
                        .whiteSpace(WhiteSpace.NoWrap)
                        .margin(topBottom = 15.px),
                    text = "Women Wellness"
                )
                SpanText(
                    modifier = getBodyTextModifier(),
                    text = "The body is of utmost importance for our holistic health as it is here that the mental and spiritual realms reside."
                )
            }
        }
    }
}


@Composable
fun YogaOptions() {
    SimpleGrid(
        modifier = Modifier.fillMaxWidth(80.percent)
            .margin(bottom = 120.px),
        numColumns = numColumns(base = 1, sm = 2, md = 4, lg = 4)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(10.px)
                .margin(leftRight = 20.px)
                .background(YogaVedaTheme.Colors.White.rgb)
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
                    modifier = getTextModifier()
                        .fontSize(24.px)
                        .margin(topBottom = 15.px),
                    text = "General Fitness"
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(10.px)
                .margin(leftRight = 20.px)
                .background(YogaVedaTheme.Colors.White.rgb)
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
                    modifier = getTextModifier()
                        .fontSize(24.px)
                        .margin(topBottom = 15.px),
                    text = "Prenatal Yoga"
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(10.px)
                .margin(leftRight = 20.px)
                .background(YogaVedaTheme.Colors.White.rgb)
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
                    modifier = getTextModifier()
                        .fontSize(24.px)
                        .margin(topBottom = 15.px),
                    text = "Postnatal Yoga"
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(10.px)
                .margin(leftRight = 20.px)
                .background(YogaVedaTheme.Colors.White.rgb)
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
                    modifier = getTextModifier()
                        .fontSize(24.px)
                        .margin(topBottom = 15.px),
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
            modifier = getTextModifier()
                .fontSize(24.px)
                .margin(topBottom = 15.px)
                .padding(bottom = 60.px)
                .color(YogaVedaTheme.Colors.BlackGrey.rgb)
                .fontWeight(FontWeight.Light),
            text = "Invest in your health.\n" +
                    "Studio Yogaveda, Gurgaon"
        )
        Button(
            attrs = YVButtonStyle.toModifier()
                .then(getButtonModifier())
                //.border(1.px, LineStyle.Solid, YogaVedaTheme.Orange.rgb)
                .onClick {
                    window.open(
                        "tel:+919718920120",
                        OpenLinkStrategy.IN_NEW_TAB.toString()
                    )
                }
                .background(YogaVedaTheme.Colors.White.rgb)
                .toAttrs(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                FaPhone(
                    modifier = Modifier
                        .color(YogaVedaTheme.Colors.Orange.rgb)
                        .margin(right = 15.px)
                )
                SpanText(
                    modifier = Modifier
                        .fontFamily("Archivo", "Arial")
                        .fontSize(14.px)
                        .fontWeight(FontWeight.SemiBold)
                        .color(YogaVedaTheme.Colors.Orange.rgb),
                    text = "+(91) 9718-920-120"
                )
            }
        }
    }
}

