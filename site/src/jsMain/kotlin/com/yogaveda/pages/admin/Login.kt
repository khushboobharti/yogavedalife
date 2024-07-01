package com.yogaveda.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.yogaveda.models.User
import com.yogaveda.models.UserWithoutPassword
import com.yogaveda.navigation.Screen
import com.yogaveda.network.checkUserExistence
import com.yogaveda.styles.LoginStyle
import com.yogaveda.ui.Script
import com.yogaveda.ui.Theme
import com.yogaveda.util.Constants.FONT_FAMILY
import com.yogaveda.util.Id
import com.yogaveda.util.Res
import com.yogaveda.util.noBorder
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.set

@Page
@Composable
fun LoginScreen() {
    val scope = rememberCoroutineScope()
    val context = rememberPageContext()
    var errorText by remember { mutableStateOf("") }
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(leftRight = 50.px, top = 80.px, bottom = 24.px)
                .backgroundColor(Theme.LightGray.rgb),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                src = Res.Image.logo,
                modifier = Modifier
                    .margin(bottom = 50.px)
                    .width(100.px),
                alt = "Logo Image"
            )
            Input(
                type = InputType.Text,
                attrs = LoginStyle.toModifier()
                    .id(Id.usernameInput)
                    .margin(bottom = 12.px)
                    .width(350.px)
                    .height(54.px)
                    .padding(leftRight = 20.px)
                    .backgroundColor(Colors.White)
                    .fontFamily(FONT_FAMILY)
                    .fontWeight(FontWeight.Medium)
                    .toAttrs {
                        attr("placeholder", "Username")
                    }
            )
            Input(
                type = InputType.Text,
                attrs = LoginStyle.toModifier()
                    .id(Id.passwordInput)
                    .margin(bottom = 12.px)
                    .width(350.px)
                    .height(54.px)
                    .padding(leftRight = 20.px)
                    .backgroundColor(Colors.White)
                    .fontFamily(FONT_FAMILY)
                    .fontWeight(FontWeight.Medium)
                    .toAttrs {
                        attr("placeholder", "Password")
                    }
            )
            Button(
                attrs = Modifier
                    .width(350.px)
                    .height(54.px)
                    .margin(bottom = 24.px)
                    .backgroundColor(Theme.Primary.rgb)
                    .color(Colors.White)
                    .borderRadius(r = 4.px)
                    .fontFamily(FONT_FAMILY)
                    .fontWeight(FontWeight.Medium)
                    .fontSize(14.px)
                    .noBorder()
                    .cursor(Cursor.Pointer)
                    .onClick {
                        val username = (document.getElementById(Id.usernameInput) as HTMLInputElement).value
                        val password = (document.getElementById(Id.passwordInput) as HTMLInputElement).value
                        scope.launch {
                            if(username.isNotEmpty() && password.isNotEmpty()) {
                                val user = checkUserExistence(
                                    user = User(
                                        username = username,
                                        password = password
                                    )
                                )
                                if(user != null) {
                                    rememberLoggedIn(remember = true, user = user)
                                    context.router.navigateTo(Screen.AdminHome.route)
                                } else {
                                    errorText = "User does not exist."
                                    delay(3000)
                                    errorText = ""
                                }
                            } else {
                                errorText = "Input fields are empty."
                                delay(3000)
                                errorText = ""
                            }
                        }
                    }
                    .toAttrs {}
            ) {
                SpanText(text = "Sign In")
            }
            SpanText(
                modifier = Modifier
                    .width(350.px)
                    .color(Colors.Red)
                    .textAlign(TextAlign.Center),
                text = errorText
            )

            Div {
                /*Script (
                    isAsync = false,
                    src = "https://apis.google.com/js/platform.js",
                    attrs = Modifier
                        .toAttrs{
                            attr("onload", "console.log(\"received\")")
                        }
                )*/

                Script {
                    Text(value = "function handleCredentialResponse(response) { console.log(response.credential); }")
                    Text(value = "function initializeGoogle() {\n" +
                            "        google.accounts.id.initialize({\n" +
                            "          client_id: \"455201288000-9rummi3auo4t2efq64ekn1t64vuhdcv6.apps.googleusercontent.com\",\n" +
                            "          callback: handleCredentialResponse\n" +
                            "        });\n" +
                            "        google.accounts.id.renderButton(\n" +
                            "          document.getElementById(\"buttonDiv\"),\n" +
                            "          { theme: \"outline\", size: \"large\" }\n" +
                            "        );\n" +
                            "        google.accounts.id.prompt();\n" +
                            "      }")
                }
                Script (
                    isAsync = true,
                    src = "https://apis.google.com/gsi/client", //"https://apis.google.com/js/platform.js",
                    attrs = Modifier
                        .toAttrs {
                            attr("onload", "initializeGoogle")
                        }
                ) {

                }
                Div(
                    attrs = Modifier
                        .id("buttonDiv")
                        .toAttrs()
                ) {}
            }
            /*Div (
                attrs = Modifier
                    .toAttrs {
                        attr("id","g_id_onload")
                        attr("data-client_id","455201288000-9rummi3auo4t2efq64ekn1t64vuhdcv6.apps.googleusercontent.com")
                        attr("data-context","signin")
                        attr("data-ux_mode","popup")
                        attr("data-callback","handleCredentialResponse")
                        attr("data-nonce","")
                        attr("data-itp_support","true")
                        attr("data-use_fedcm_for_prompt", "true")
                    }
            )
            Div (
                attrs = Modifier
                    .toAttrs {
                        attr("class","g_id_signin")
                        attr("data-type","standard")
                        attr("data-shape","rectangular")
                        attr("data-theme","outline")
                        attr("data-text","signin_with")
                        attr("data-size","large")
                        attr("data-logo_alignment","left")
                    }
            )*/



        }
    }
}

private fun rememberLoggedIn(
    remember: Boolean,
    user: UserWithoutPassword? = null
) {
    localStorage["remember"] = remember.toString()
    if(user != null) {
        localStorage["userId"] = user.id
        localStorage["username"] = user.username
    }
}