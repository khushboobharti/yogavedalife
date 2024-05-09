package com.yogaveda.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.icons.fa.FaInfo
import com.varabyte.kobweb.silk.components.icons.fa.FaXmark
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.yogaveda.ui.Theme
import com.yogaveda.util.Constants
import com.yogaveda.util.Res.Icon.code
import org.jetbrains.compose.web.css.dppx
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Page
@Composable
fun HomePage() {
    /* val context = rememberPageContext()
     val scope = rememberCoroutineScope()
     val breakpoint = rememberBreakpoint()
     var overflowMenuOpened by remember { mutableStateOf(false) }
     var mainPosts by remember { mutableStateOf<ApiListResponse>(ApiListResponse.Idle) }
     val latestPosts = remember { mutableStateListOf<PostWithoutDetails>() }
     val sponsoredPosts = remember { mutableStateListOf<PostWithoutDetails>() }
     val popularPosts = remember { mutableStateListOf<PostWithoutDetails>() }
     var latestPostsToSkip by remember { mutableStateOf(0) }
     var popularPostsToSkip by remember { mutableStateOf(0) }
     var showMoreLatestPosts by remember { mutableStateOf(false) }
     var showMorePopularPosts by remember { mutableStateOf(false) }*/

    val context = rememberPageContext()
    var showInfoLayout by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {

    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (!showInfoLayout) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(36.px),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight(20.percent)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SpanText(
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        text = "YogaVeda Life"
                    )
                    FaInfo(
                        modifier = Modifier
                            .color(Theme.Secondary.rgb)
                            .onClick {
                                showInfoLayout = true
                                /*

                                 Show about US layout and change the icon to cross
                                  */
                            },
                        size = IconSize.LG
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxHeight(80.percent)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1)
                            .fillMaxHeight(75.percent)
                            .backgroundColor(color = Theme.Primary.rgb)
                            .margin(leftRight = 8.px)
                            .cursor(Cursor.Pointer),
                        contentAlignment = Alignment.Center
                    ) {
                        SpanText(
                            modifier = Modifier
                                .margin(bottom = 24.px)
                                .fontFamily(Constants.FONT_FAMILY)
                                .fontSize(18.px)
                                .fontWeight(FontWeight.Medium),
                            text = "Conscious Living"
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1)
                            .fillMaxHeight(75.percent)
                            .backgroundColor(color = Theme.Green.rgb)
                            .margin(leftRight = 8.px)
                            .cursor(Cursor.Pointer),
                        contentAlignment = Alignment.Center
                    ) {
                        SpanText(
                            modifier = Modifier
                                .margin(bottom = 24.px)
                                .fontFamily(Constants.FONT_FAMILY)
                                .fontSize(18.px)
                                .fontWeight(FontWeight.Medium),
                            text = "Recommendations"
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1)
                            .fillMaxHeight(75.percent)
                            .backgroundColor(color = Theme.Yellow.rgb)
                            .margin(leftRight = 8.px)
                            .cursor(Cursor.Pointer)
                            .onClick {
                                //val code = js(code = "onClick(\"https://studioyogaveda.com\")")
                                val code = js(code = "window.open('https://studioyogaveda.in', '_blank').focus();")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        SpanText(
                            modifier = Modifier
                                .margin(bottom = 24.px)
                                .fontFamily(Constants.FONT_FAMILY)
                                .fontSize(18.px)
                                .fontWeight(FontWeight.Medium),
                            text = "Studio YogaVeda"
                        )
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.px),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SpanText(
                        modifier = Modifier
                            .margin(bottom = 24.px)
                            .fontFamily(Constants.FONT_FAMILY)
                            .fontSize(18.px)
                            .fontWeight(FontWeight.Medium),
                        text = "Studio YogaVeda"
                    )
                    SpanText(
                        modifier = Modifier
                            .margin(bottom = 24.px)
                            .fontFamily(Constants.FONT_FAMILY)
                            .fontSize(18.px)
                            .fontWeight(FontWeight.Medium),
                        text = "Studio YogaVeda"
                    )
                    SpanText(
                        modifier = Modifier
                            .margin(bottom = 24.px)
                            .fontFamily(Constants.FONT_FAMILY)
                            .fontSize(18.px)
                            .fontWeight(FontWeight.Medium),
                        text = "Studio YogaVeda"
                    )
                }
                FaXmark(
                    modifier = Modifier
                        .color(Theme.Secondary.rgb)
                        .margin(48.px)
                        .align(Alignment.TopEnd)
                        .onClick { showInfoLayout = false },
                    size = IconSize.LG
                )
            }
        }
    }
}
