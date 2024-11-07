package com.yogaveda.components.yoga

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextDecorationLine
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.onTouchEnd
import com.varabyte.kobweb.compose.ui.modifiers.textDecorationLine
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.yogaveda.navigation.Screen
import com.yogaveda.styles.modifiers.MenuItemStyle
import com.yogaveda.ui.Theme
import com.yogaveda.ui.YogaVedaTheme
import com.yogaveda.util.Constants
import com.yogaveda.util.Constants.HEADER_HEIGHT
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun YogaHeader(

) {
    val breakpoint = rememberBreakpoint()
    val selectedMenuItem = if (breakpoint >= Breakpoint.LG) "yoga" else null

    Row(
        modifier = Modifier
            .fillMaxWidth(if (breakpoint > Breakpoint.MD) 80.percent else 90.percent)
            .height(HEADER_HEIGHT.px)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Logo
        HeaderMenu()
    }
}

@Composable
fun HeaderMenu(breakpoint: Breakpoint = rememberBreakpoint(), selectedMenuItem: String? = null) {
    if (breakpoint >= Breakpoint.LG) {
        MenuNavigationItems(selectedMenuItem = selectedMenuItem?.let { MenuItems.valueOf(it) } ?: MenuItems.Home)
    }
}

@Composable
fun MenuNavigationItems(
    selectedMenuItem: MenuItems,
    vertical: Boolean = false,
) {
    val context = rememberPageContext()
    MenuItems.entries.forEach { menuItem ->
        Link(
            modifier = MenuItemStyle.toModifier()
                .thenIf(
                    condition = vertical,
                    other = Modifier.margin(bottom = 24.px)
                )
                .thenIf(
                    condition = !vertical,
                    other = Modifier.margin(right = 24.px)
                )
                .thenIf(
                    condition = selectedMenuItem == menuItem,
                    other = Modifier.color(YogaVedaTheme.Colors.Orange.rgb)
                )
                .fontFamily(Constants.FONT_FAMILY)
                .fontSize(16.px)
                .fontWeight(FontWeight.Medium)
                .textDecorationLine(TextDecorationLine.None)
                .onClick { context.router.navigateTo(Screen.YogaPage.section(menuItem.route)) }
                .onTouchEnd { context.router.navigateTo(Screen.YogaPage.section(menuItem.route)) },
            path = "",
            text = menuItem.name
        )
    }
}

enum class MenuItems(val route: String) {
    Home(route = "Home"),
    Classes(route = "Classes"),
    Testimonials(route = "Testimonials"),
    Consult(route = "Consult"),
    Shop(route = "Shop"),
    Blogs(route = "Blogs"),;
    //AboutUs(name = "About Us"),
    //ContactUs(name = "Contact Us");
}
/**
 * Classes, Testimonials, Consult, Shop, Blogs
 */