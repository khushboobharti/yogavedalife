package com.yogaveda.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.yogaveda.styles.NewsletterInputStyle
import com.yogaveda.util.Id
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.browser.document
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Input
import org.w3c.dom.HTMLInputElement

@Composable
fun LoginView(auth: FirebaseAuth) {
    val scope = rememberCoroutineScope()
    var firebaseUser: FirebaseUser? by remember { mutableStateOf(null) }
    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize().zIndex(1f),
        contentAlignment = Alignment.Center
    ) {

        if (firebaseUser == null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Input(
                    type = InputType.Text,
                    attrs = NewsletterInputStyle.toModifier()
                        .id(Id.emailInput)
                        .width(320.px)
                        .height(54.px)
                        .toAttrs {
                            attr("placeholder", "Your Email Address")
                        }
                )
                Spacer()
                Input(
                    type = InputType.Text,
                    attrs = NewsletterInputStyle.toModifier()
                        .id(Id.passwordInput)
                        .width(320.px)
                        .height(54.px)
                        .toAttrs {
                            attr("placeholder", "Password")
                        },
                )
                Spacer()
                Button(
                    attrs = Modifier
                        .onClick {
                            userEmail = (document.getElementById(Id.emailInput) as HTMLInputElement).value
                            userPassword = (document.getElementById(Id.passwordInput) as HTMLInputElement).value
                            scope.launch {
                                try {
                                    auth.createUserWithEmailAndPassword(
                                        email = userEmail,
                                        password = userPassword
                                    )
                                    firebaseUser = auth.currentUser
                                } catch (e: Exception) {
                                    auth.signInWithEmailAndPassword(
                                        email = userEmail,
                                        password = userPassword
                                    )
                                    firebaseUser = auth.currentUser
                                }
                            }
                        }.toAttrs()
                ) {
                    SpanText(text = "Sign IN")
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpanText(text = firebaseUser?.uid ?: "Unknown ID")
                Spacer()
                Button(
                    attrs = Modifier
                        .onClick {
                            scope.launch {
                                auth.signOut()
                                firebaseUser = auth.currentUser
                            }
                        }.toAttrs()
                ) {
                    SpanText(text = "Sign Out")
                }
            }
        }
    }
}
