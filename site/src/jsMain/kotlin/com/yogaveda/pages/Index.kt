package com.yogaveda.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.yogaveda.Constants.POSTS_PER_PAGE
import com.yogaveda.components.CategoryNavigationItems
import com.yogaveda.components.OverflowSidePanel
import com.yogaveda.models.ApiListResponse
import com.yogaveda.models.PostWithoutDetails
import com.yogaveda.network.fetchLatestPosts
import com.yogaveda.network.fetchMainPosts
import com.yogaveda.network.fetchPopularPosts
import com.yogaveda.network.fetchSponsoredPosts
import com.yogaveda.sections.HeaderSection
import com.yogaveda.sections.MainSection
import com.yogaveda.sections.PostsSection
import com.yogaveda.sections.SponsoredPostsSection
import kotlinx.coroutines.launch

@Page
@Composable
fun HomePage() {
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
    var showMorePopularPosts by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        fetchMainPosts(
            skip = 0,
            onSuccess = {
                mainPosts = it
            },
            onError = {}
        )
        fetchLatestPosts(
            skip = latestPostsToSkip,
            onSuccess = {
                if (it is ApiListResponse.Success) {
                    latestPosts.addAll(it.data)
                    latestPostsToSkip += POSTS_PER_PAGE
                    if (it.data.size >= POSTS_PER_PAGE) showMoreLatestPosts = true
                }
            },
            onError = {}
        )
        fetchSponsoredPosts(
            onSuccess = {
                if (it is ApiListResponse.Success) {
                    sponsoredPosts.addAll(it.data)
                }
            },
            onError = {}
        )
        fetchPopularPosts(
            skip = popularPostsToSkip,
            onSuccess = {},
            onError = {}
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (overflowMenuOpened) {
            OverflowSidePanel(
                onMenuClose = { overflowMenuOpened = false },
                content = { CategoryNavigationItems(vertical = true) }
            )
        }
        HeaderSection(
            breakpoint = breakpoint,
            selectedCategory = null,
            onMenuOpen = { overflowMenuOpened = true }
        )
        MainSection(breakpoint = breakpoint, posts = mainPosts, onClick = {})
        PostsSection(
            breakpoint = breakpoint,
            posts = latestPosts,
            title = "Latest Posts",
            showMoreVisibility = true,
            onShowMore = {
                scope.launch {
                    fetchLatestPosts(
                        skip = latestPostsToSkip,
                        onSuccess = { response ->
                            if (response is ApiListResponse.Success) {
                                if (response.data.isNotEmpty()) {
                                    if (response.data.size < POSTS_PER_PAGE) showMoreLatestPosts =
                                        false

                                    latestPosts.addAll(response.data)
                                    latestPostsToSkip += POSTS_PER_PAGE
                                } else {
                                    showMoreLatestPosts = false
                                }
                            }
                        },
                        onError = {}
                    )
                }
            },
            onClick = {}
        )
        SponsoredPostsSection(
            breakpoint = breakpoint,
            posts = sponsoredPosts,
            onClick = {}
        )
    }
}
