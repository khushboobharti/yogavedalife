package com.yogaveda.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.TextTransform
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.textTransform
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.yogaveda.components.yoga.YVText
import com.yogaveda.models.Newsletter
import com.yogaveda.network.subscribeToNewsletter
import com.yogaveda.ui.Theme
import com.yogaveda.ui.YogaVedaTheme
import com.yogaveda.util.Constants.FONT_ARCHIVO
import com.yogaveda.util.Constants.FONT_FAMILY
import com.yogaveda.util.Constants.FONT_GOTU
import com.yogaveda.util.Id
import com.yogaveda.util.noBorder
import com.yogaveda.util.validateEmail
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.CSSUnit.Companion.percent
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLInputElement

@Page
@Composable
fun YogaPage() {

    val context = rememberPageContext()
    val scope = rememberCoroutineScope()
    val breakpoint = rememberBreakpoint()


    val windowHeight = remember { mutableStateOf(window.innerHeight) }  //remember()
    /*// TODO: Replace the following with your own content
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("THIS IS A YOGA PAGE")
    }*/

    LaunchedEffect(Unit) {

        window.addEventListener("resize", {
            windowHeight.value = window.innerHeight
        })
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxSize() //.fillMaxWidth(100.percent)
                .backgroundColor(YogaVedaTheme.BackgroundWhitish.rgb),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row (
                    modifier = Modifier
                        .height(windowHeight.value.px)
                        .fillMaxWidth(80.percent),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    YVText(modifier = Modifier.transition(CSSTransition(property = "scale", duration = 200.ms)), text = "Sat")
                    YVText(text = "Chitt", modifier = Modifier.color(YogaVedaTheme.OrangeFaded.rgb))
                    YVText(text = "Anand")
                }
                Button(
                    attrs = Modifier
                        .onClick {}
                        .height(54.px)
                        .backgroundColor(Theme.Primary.rgb)
                        .borderRadius(100.px)
                        .padding(leftRight = 50.px)
                        .noBorder()
                        .cursor(Cursor.Pointer)
                        .toAttrs()
                ) {
                    SpanText(
                        modifier = Modifier
                            .fontFamily(FONT_FAMILY)
                            .fontSize(18.px)
                            .fontWeight(FontWeight.Medium)
                            .color(Colors.White),
                        text = "Join Our Classes"
                    )
                }
            }

            //Text("THIS IS A YOGA PAGE")
            /*SpanText(
                modifier = Modifier
                    .textAlign(TextAlign.Center)
                    .color(YogaVedaTheme.BlackGrey.rgb)
                    .fontFamily("Gotu")
                    .fontSize(130.px)
                    .lineHeight(1.1.em)
                    .textTransform(TextTransform.Lowercase)
                    .fontWeight(FontWeight.Bold),
                text = "Body Mind"
            )*/
        }
    }
}