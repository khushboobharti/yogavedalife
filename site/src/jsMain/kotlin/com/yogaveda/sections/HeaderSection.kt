@file:Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")

package com.yogaveda.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaBars
import com.varabyte.kobweb.silk.components.icons.fa.FaXmark
import com.varabyte.kobweb.silk.components.icons.fa.IconSize
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.text.SpanText
import com.yogaveda.components.CategoryNavigationItems
import com.yogaveda.components.SearchBar
import com.yogaveda.models.Category
import com.yogaveda.models.User
import com.yogaveda.navigation.Screen
import com.yogaveda.ui.Theme
import com.yogaveda.util.Constants
import com.yogaveda.util.Constants.HEADER_HEIGHT
import com.yogaveda.util.Constants.PAGE_WIDTH
import com.yogaveda.util.Id
import com.yogaveda.util.Res
import dev.gitlive.firebase.auth.externals.Auth
import dev.gitlive.firebase.auth.externals.AuthError
import dev.gitlive.firebase.auth.externals.GoogleAuthProvider
import dev.gitlive.firebase.auth.externals.signInWithPopup
import kotlinx.browser.document
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.w3c.dom.HTMLInputElement

@Composable
fun HeaderSection(
    breakpoint: Breakpoint,
    selectedCategory: Category? = null,
    logo: String = Res.Image.logoHome,
    onMenuOpen: () -> Unit,
    auth: Auth,
    provider: GoogleAuthProvider,
    scope: CoroutineScope
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .backgroundColor(Theme.Secondary.rgb),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .backgroundColor(Theme.Secondary.rgb)
                .maxWidth(PAGE_WIDTH.px),
            contentAlignment = Alignment.TopCenter
        ) {
            Header(
                breakpoint = breakpoint,
                logo = logo,
                selectedCategory = selectedCategory,
                onMenuOpen = onMenuOpen,
                auth = auth,
                provider = provider,
                scope = scope
            )
        }
    }
}

@Composable
fun Header(
    breakpoint: Breakpoint,
    logo: String,
    selectedCategory: Category?,
    onMenuOpen: () -> Unit,
    auth: Auth,
    provider: GoogleAuthProvider,
    scope: CoroutineScope
) {
    val context = rememberPageContext()
    var fullSearchBarOpened by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth(if (breakpoint > Breakpoint.MD) 80.percent else 90.percent)
            .height(HEADER_HEIGHT.px),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (breakpoint <= Breakpoint.MD) {
            if (fullSearchBarOpened) {
                FaXmark(
                    modifier = Modifier
                        .margin(right = 24.px)
                        .color(Colors.White)
                        .cursor(Cursor.Pointer)
                        .onClick { fullSearchBarOpened = false },
                    size = IconSize.XL
                )
            }
            if (!fullSearchBarOpened) {
                FaBars(
                    modifier = Modifier
                        .margin(right = 24.px)
                        .color(Colors.White)
                        .cursor(Cursor.Pointer)
                        .onClick { onMenuOpen() },
                    size = IconSize.XL
                )
            }
        }
        if (!fullSearchBarOpened) {
            Image(
                modifier = Modifier
                    .margin(right = 50.px)
                    .width(if (breakpoint >= Breakpoint.SM) 100.px else 70.px)
                    .cursor(Cursor.Pointer)
                    .onClick { context.router.navigateTo(Screen.HomePage.route) },
                src = logo,
                alt = "Logo Image"
            )
        }
        if (breakpoint >= Breakpoint.LG) {
            CategoryNavigationItems(selectedCategory = selectedCategory)
        }
        Spacer()
        SearchBar(
            breakpoint = breakpoint,
            fullWidth = fullSearchBarOpened,
            darkTheme = true,
            onEnterClick = {
                val query = (document.getElementById(Id.adminSearchBar) as HTMLInputElement).value
                context.router.navigateTo(Screen.SearchPage.searchByTitle(query = query))
            },
            onSearchIconClick = { fullSearchBarOpened = it }
        )
        SignInComponent(
            user = null,
            auth = auth,
            provider = provider,
            scope = scope
        )
        /*Image(
            modifier = Modifier.margin(bottom = 24.px)
                .onClick {
                    signInWithPopup(auth = auth, provider)
                        .then { result ->
                            console.log("Auth Successful")
                            if(result.user.emailVerified) {
                                val credentials = GoogleAuthProvider.credentialFromResult(result)
                                val accessToken = credentials?.accessToken
                                val refreshTokenResult = credentials?.secret
                                val displayName = result.user.displayName
                                val email = result.user.email
                                //val isEmailVerified = result.user.emailVerified
                                val phoneNumber = result.user.phoneNumber
                                val photoURL = result.user.photoURL
                                val providerId = result.user.providerId

                                scope.launch {

                                }

                            } else {
                                console.log("Email not verified")
                            }
                        }
                        .catch { exception ->
                            console.log("Auth failed")
                            val error = exception as AuthError
                            console.log(error)
                            val credential = GoogleAuthProvider.credentialFromError(error)
                            console.log(credential?.signInMethod)
                        }
                },
            src = Res.Icon.google,
            alt = "Google Login Icon"
        )*/
    }
}

@Composable
fun SignInComponent(
    user: User?,
    auth: Auth,
    provider: GoogleAuthProvider,
    scope: CoroutineScope
) {
    Box(
        modifier = Modifier
            .borderRadius(r = 8.px)
            .border(
                width = 1.px,
                style = LineStyle.Solid,
                color = Theme.White.rgb //if(darkTheme) Theme.entries.find { it.hex == category.color }?.rgb else Theme.HalfBlack.rgb
            )
            .onClick {
                signInWithPopup(auth = auth, provider)
                    .then { result ->
                        console.log("Auth Successful")
                        if(result.user.emailVerified) {
                            val credentials = GoogleAuthProvider.credentialFromResult(result)
                            val accessToken = credentials?.accessToken
                            val refreshTokenResult = credentials?.secret
                            val displayName = result.user.displayName
                            val email = result.user.email
                            //val isEmailVerified = result.user.emailVerified
                            val phoneNumber = result.user.phoneNumber
                            val photoURL = result.user.photoURL
                            val providerId = result.user.providerId

                            scope.launch {
                                // cal APi function to send user data
                                // on response set user and cookies
                            }

                        } else {
                            console.log("Email not verified")
                        }
                    }
                    .catch { exception ->
                        console.log("Auth failed")
                        val error = exception as AuthError
                        console.log(error)
                        val credential = GoogleAuthProvider.credentialFromError(error)
                        console.log(credential?.signInMethod)
                    }
            },
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .height(48.px),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.margin(leftRight = 4.px)
                    .onClick {

                    },
                src = if(user != null) Res.Icon.google else Res.Icon.google,
                alt = "Google Login Icon",
                width = 32,
                height = 32
            )
            SpanText(
                modifier = Modifier
                    .margin(bottom = 24.px)
                    .fontFamily(Constants.FONT_FAMILY)
                    .fontSize(18.px)
                    .margin(leftRight = 8.px)
                    .color(Theme.White.rgb)
                    .fontWeight(FontWeight.Medium),
                text = if(user != null) "Profile" else "Sign In"
            )
        }
    }
}