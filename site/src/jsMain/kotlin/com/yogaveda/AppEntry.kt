package com.yogaveda

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.components.style.common.SmoothColorStyle
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.initialize
import org.jetbrains.compose.web.css.vh

@InitSilk
fun initSilk(ctx: InitSilkContext) {
    // Configure silk here
    val firebaseOptions  = FirebaseOptions(
        applicationId = "1:455201288000:web:f99a6a7ef3527ab935d4a5",
        apiKey = "AIzaSyD1ElrWyP8kZRsPHqWxyuGQmoqSdMScK4A",
        databaseUrl = null,
        gaTrackingId = null,
        storageBucket = "yoga-veda-425712.appspot.com",
        projectId = "yoga-veda-425712",
        gcmSenderId = null,
        authDomain = "yoga-veda-425712.firebaseapp.com"
    )
    Firebase.initialize(ctx, firebaseOptions)
}

@App
@Composable
fun MyApp(content: @Composable () -> Unit) {
    SilkApp {
        Surface(SmoothColorStyle.toModifier().minHeight(100.vh)) {
            content()
        }
    }
}
