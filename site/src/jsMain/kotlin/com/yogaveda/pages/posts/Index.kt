package com.yogaveda.pages.posts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.yogaveda.Constants.POSTS_PER_PAGE
import com.yogaveda.components.CategoryNavigationItems
import com.yogaveda.components.OverflowSidePanel
import com.yogaveda.models.ApiListResponse
import com.yogaveda.models.PostWithoutDetails
import com.yogaveda.models.User
import com.yogaveda.navigation.Screen
import com.yogaveda.network.fetchLatestPosts
import com.yogaveda.network.fetchMainPosts
import com.yogaveda.network.fetchPopularPosts
import com.yogaveda.network.fetchSponsoredPosts
import com.yogaveda.sections.AdminFooterSection
import com.yogaveda.sections.HeaderSection
import com.yogaveda.sections.MainSection
import com.yogaveda.sections.NewsletterSection
import com.yogaveda.sections.PostsSection
import com.yogaveda.sections.SponsoredPostsSection
import com.yogaveda.util.getLocalUser
import com.yogaveda.util.saveLocalUser
import dev.gitlive.firebase.auth.externals.GoogleAuthProvider
import dev.gitlive.firebase.auth.externals.getAuth
import kotlinx.coroutines.launch


@Page
@Composable
fun HomePage() {
    val context = rememberPageContext()
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
    var localUser by remember { mutableStateOf<User?>(getLocalUser()) }

    val auth = remember { getAuth() }
    val provider = remember { GoogleAuthProvider() }
    provider.addScope("https://www.googleapis.com/auth/userinfo.email")
    provider.addScope("https://www.googleapis.com/auth/userinfo.profile")
    provider.addScope("openid")


    LaunchedEffect(key1 = localUser) {
        println("local user: ${localUser?.email}")

        fetchMainPosts(
            skip = 0,
            onSuccess = {
                mainPosts = it
            },
            onError = { print(it) }
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
            onError = { print(it) }
        )
        fetchSponsoredPosts(
            onSuccess = {
                if (it is ApiListResponse.Success) {
                    sponsoredPosts.addAll(it.data)
                }
            },
            onError = { print(it) }
        )
        fetchPopularPosts(
            skip = popularPostsToSkip,
            onSuccess = {
                if (it is ApiListResponse.Success) {
                    popularPosts.addAll(it.data)
                    popularPostsToSkip += POSTS_PER_PAGE
                    if (it.data.size >= POSTS_PER_PAGE) showMorePopularPosts = true
                }
            },
            onError = { print(it) }
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
            onMenuOpen = { overflowMenuOpened = true },
            auth = auth,
            provider = provider,
            scope = scope,
            setGlobalUser = { authenticatedUser ->

                saveLocalUser(authenticatedUser)
                localUser = authenticatedUser
                println("User set ${authenticatedUser?.email}")
            },
            localUser = localUser
        )
        MainSection(
            breakpoint = breakpoint,
            posts = mainPosts,
            onClick = { context.router.navigateTo(Screen.PostPage.getPost(id = it)) }
        )
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
            onClick = { context.router.navigateTo(Screen.PostPage.getPost(id = it)) }
        )
        SponsoredPostsSection(
            breakpoint = breakpoint,
            posts = sponsoredPosts,
            onClick = { context.router.navigateTo(Screen.PostPage.getPost(id = it))}
        )
        PostsSection(
            breakpoint = breakpoint,
            posts = popularPosts,
            title = "Popular Posts",
            showMoreVisibility = true,
            onShowMore = {
                scope.launch {
                    fetchPopularPosts(
                        skip = popularPostsToSkip,
                        onSuccess = { response ->
                            if (response is ApiListResponse.Success) {
                                if (response.data.isNotEmpty()) {
                                    if (response.data.size < POSTS_PER_PAGE) showMorePopularPosts = false

                                    popularPosts.addAll(response.data)
                                    popularPostsToSkip += POSTS_PER_PAGE
                                } else {
                                    showMorePopularPosts = false
                                }
                            }
                        },
                        onError = {}
                    )
                }
            },
            onClick = { context.router.navigateTo(Screen.PostPage.getPost(id = it))}
        )
        NewsletterSection(breakpoint = breakpoint)
        AdminFooterSection()
    }
}