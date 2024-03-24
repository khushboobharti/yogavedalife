package com.yogaveda.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.FontWeight
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
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Switch
import com.varabyte.kobweb.silk.components.forms.SwitchSize
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.yogaveda.components.AdminPageLayout
import com.yogaveda.components.Posts
import com.yogaveda.components.SearchBar
import com.yogaveda.models.ApiListResponse
import com.yogaveda.models.PostWithoutDetails
import com.yogaveda.network.fetchMyPosts
import com.yogaveda.ui.Theme
import com.yogaveda.util.Constants.FONT_FAMILY
import com.yogaveda.util.Constants.POSTS_PER_PAGE
import com.yogaveda.util.Constants.SIDE_PANEL_WIDTH
import com.yogaveda.util.isUserLoggedIn
import com.yogaveda.util.noBorder
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button

@Page
@Composable
fun MyPostsPage() {
    isUserLoggedIn {
        MyPostsScreen()
    }
}

@Composable
fun MyPostsScreen() {

    val scope = rememberCoroutineScope()
    val breakpoint = rememberBreakpoint()
    var myPosts = remember { mutableStateListOf<PostWithoutDetails>() }

    var postsToSkip by remember { mutableStateOf(0) }
    var showMoreVisibility by remember { mutableStateOf(false) }
    val selectedPosts = remember { mutableStateListOf<String>() }

    var selectableMode by remember { mutableStateOf(false) }
    var switchText by remember { mutableStateOf("Select") }



    LaunchedEffect(Unit) {
        fetchMyPosts(
            skip = postsToSkip,
            onSuccess = {
                if (it is ApiListResponse.Success) {
                    myPosts.clear()
                    myPosts.addAll(it.data)
                    postsToSkip += POSTS_PER_PAGE
                    showMoreVisibility = it.data.size >= POSTS_PER_PAGE
                }
            },
            onError = {
                println(it)
            }
        )
    }

    AdminPageLayout {
        Column(
            modifier = Modifier
                .margin(topBottom = 50.px)
                .fillMaxSize()
                .padding(left = if (breakpoint > Breakpoint.MD) SIDE_PANEL_WIDTH.px else 0.px),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(
                        if (breakpoint > Breakpoint.MD) 30.percent
                        else 50.percent
                    )
                    .margin(bottom = 24.px),
                contentAlignment = Alignment.Center
            ) {
                SearchBar(
                    breakpoint = breakpoint,
                    modifier = Modifier,
                    onEnterClick = {},
                    onSearchIconClick = {}
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(
                        if (breakpoint > Breakpoint.MD) 80.percent
                        else 90.percent
                    )
                    .margin(bottom = 24.px),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(
                        modifier = Modifier.margin(right = 8.px),
                        size = SwitchSize.LG,
                        checked = selectableMode,
                        onCheckedChange = {
                            selectableMode = it
                            if (!selectableMode) {
                                switchText = "Select"
                                selectedPosts.clear()
                            } else {
                                switchText = "0 Posts Selected"
                            }
                        }
                    )
                    SpanText(
                        modifier = Modifier.color(if (selectableMode) Colors.Black else Theme.HalfBlack.rgb),
                        text = switchText
                    )
                }
                Button(
                    attrs = Modifier
                        .margin(right = 20.px)
                        .height(54.px)
                        .padding(leftRight = 24.px)
                        .backgroundColor(Theme.Red.rgb)
                        .color(Colors.White)
                        .noBorder()
                        .borderRadius(r = 4.px)
                        .fontFamily(FONT_FAMILY)
                        .fontSize(14.px)
                        .fontWeight(FontWeight.Medium)
                        //.visibility(if (selectedPosts.isNotEmpty()) Visibility.Visible else Visibility.Hidden)
                        .onClick {
                            scope.launch {
                                val result = deleteSelectedPosts(ids = selectedPosts)
                                if (result) {
                                    selectableMode = false
                                    switchText = "Select"
                                    /*postsToSkip -= selectedPosts.size
                                    selectedPosts.forEach { deletedPostId ->
                                        myPosts.removeAll {
                                            it._id == deletedPostId
                                        }
                                    }*/
                                    selectedPosts.clear()
                                }
                            }
                        }
                        .toAttrs()
                ) {
                    SpanText(text = "Delete")
                }
            }
            Posts(
                breakpoint = breakpoint,
                showMoreVisibility = showMoreVisibility,
                onShowMore = {
                    scope.launch {
                        fetchMyPosts(
                            skip = postsToSkip,
                            onSuccess = {
                                if (it is ApiListResponse.Success) {
                                    if (it.data.isNotEmpty()) {
                                        //myPosts.clear()
                                        myPosts.addAll(it.data)
                                        postsToSkip += POSTS_PER_PAGE
                                        if(it.data.size < POSTS_PER_PAGE)
                                            showMoreVisibility = false
                                        //showMoreVisibility = it.data.size >= POSTS_PER_PAGE
                                    } else {
                                        showMoreVisibility = false
                                    }
                                }
                            },
                            onError = {
                                println(it)
                            }
                        )
                    }
                },
                posts = myPosts
            )
        }
    }
}

fun deleteSelectedPosts(ids: List<String>): Boolean {
    val result = false
    return result
}