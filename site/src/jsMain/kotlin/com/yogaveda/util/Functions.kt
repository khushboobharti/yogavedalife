package com.yogaveda.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.core.rememberPageContext
import com.yogaveda.models.ControlStyle
import com.yogaveda.models.EditorControl
import com.yogaveda.models.User
import com.yogaveda.navigation.Screen
import com.yogaveda.network.checkUserId
import kotlinx.browser.document
import kotlinx.browser.localStorage
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.js.Date

@Composable
fun isUserLoggedIn(content: @Composable () -> Unit) {
    val context = rememberPageContext()
    val remembered = remember { localStorage["remember"].toBoolean() }
    val userId = remember { localStorage["userId"] }
    var userIdExists by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        userIdExists = if (!userId.isNullOrEmpty()) checkUserId(id = userId) else false
        if (!(remembered && userIdExists)) {
            context.router.navigateTo(Screen.AdminLogin.route)
        }
    }

    if (remembered && userIdExists) {
        content()
    } else {
        println("Loading...")
    }
}


fun logout() {
    localStorage["remember"] = "false"
    localStorage["userId"] = ""
    localStorage["username"] = ""
}

fun Modifier.noBorder():Modifier {
    return this
        .border(
            width = 0.px,
            style = LineStyle.None,
            color = Colors.Transparent
        )
        .outline(
            width = 0.px,
            style = LineStyle.None,
            color = Colors.Transparent
        )
}

fun getEditor() = document.getElementById(Id.editor) as HTMLTextAreaElement

fun getSelectedIntRange(): IntRange? {
    val editor = getEditor()
    val start = editor.selectionStart
    val end = editor.selectionEnd
    return if (start != null && end != null) {
        IntRange(start, (end - 1))
    } else null
}

fun getSelectedText(): String? {
    val range = getSelectedIntRange()
    return if (range != null) {
        getEditor().value.substring(range)
    } else null
}

fun applyStyle(controlStyle: ControlStyle) {
    val selectedText = getSelectedText()
    val selectedIntRange = getSelectedIntRange()
    if (selectedIntRange != null && selectedText != null) {
        getEditor().value = getEditor().value.replaceRange(
            range = selectedIntRange,
            replacement = controlStyle.style
        )
        document.getElementById(Id.editorPreview)?.innerHTML = getEditor().value
    }
}

fun applyControlStyle(
    editorControl: EditorControl,
    onLinkClick: () -> Unit,
    onImageClick: () -> Unit
) {
    when(editorControl) {
        EditorControl.Bold -> {
            applyStyle(ControlStyle.Bold(selectedText = getSelectedText()))
        }
        EditorControl.Italic -> {
            applyStyle(ControlStyle.Italic(selectedText = getSelectedText()))
        }
        EditorControl.Title -> {
            applyStyle(ControlStyle.Title(selectedText = getSelectedText()))
        }
        EditorControl.Subtitle -> {
            applyStyle(ControlStyle.Subtitle(selectedText = getSelectedText()))
        }
        EditorControl.Quote -> {
            applyStyle(ControlStyle.Quote(selectedText = getSelectedText()))
        }
        EditorControl.Code -> {
            applyStyle(ControlStyle.Code(selectedText = getSelectedText()))
        }
        EditorControl.Image -> { onImageClick() }
        EditorControl.Link -> { onLinkClick() }
    }
}

fun Long.parseDateString() = Date(this).toLocaleDateString()

fun parseSwitchText(posts: List<String>): String {
    return if (posts.isEmpty()) "No Posts selected" else if (posts.size == 1) "1 Post selected" else "${posts.size} Posts selected"
}

fun validateEmail(email: String): Boolean {
    val regex = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)"
    return regex.toRegex().matches(email)
}

fun saveLocalUser(user: User?): Boolean {
    if(user == null)  {
        localStorage["user_id"] = ""
        localStorage["display_name"] = ""
        localStorage["email"] = ""
        localStorage["phone_number"] = ""
        localStorage["photo_url"] = ""
        localStorage["provider_id"] = ""
        localStorage["access_token"] = ""
        localStorage["refresh_token"] = ""
    } else {
        localStorage["user_id"] = user.id
        localStorage["display_name"] = user.displayName
        localStorage["email"] = user.email
        localStorage["phone_number"] = user.phoneNumber
        localStorage["photo_url"] = user.photoURL
        localStorage["provider_id"] = user.providerId
        localStorage["access_token"] = user.accessToken
        localStorage["refresh_token"] = user.refreshToken
    }

    return true

    /*val userBytes = ProtoBuf.encodeToByteArray(user)
    localStorage[LOCAL_STORAGE_USER_KEY] = userBytes.toHexString()*/

    /*localStorage["role"] = user.role
    localStorage["username"] = user.username
    localStorage["remember"] = "true"
    localStorage["lastLogin"] = Date.now().toString()*/

    /*val userBytes = ProtoBuf.encodeToByteArray(user)
    localStorage[LOCAL_STORAGE_USER_KEY] = userBytes.toHexString()*/
}

fun getLocalUser(): User? {

    //val user: User? = localStorage.getItem(LOCAL_STORAGE_USER_KEY)?.let { ProtoBuf.decodeFromByteArray(it.encodeToByteArray()) }
    val user = User(
        id = localStorage["user_id"]?: "",
        displayName = localStorage["display_name"]?: "",
        email = localStorage["email"]?: "",
        phoneNumber = localStorage["phone_number"]?: "",
        photoURL = localStorage["photo_url"]?: "",
        providerId = localStorage["provider_id"]?: "",
        accessToken = localStorage["access_token"]?: "",
        refreshToken = localStorage["refresh_token"]?: ""
    )

    if(user.email.isNullOrEmpty())
        return null

    return user
}
