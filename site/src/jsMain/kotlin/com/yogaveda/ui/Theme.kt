package com.yogaveda.ui

import com.varabyte.kobweb.compose.ui.graphics.Color.Companion.rgba
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.rgb

enum class Theme(
    val hex: String,
    val rgb: CSSColorValue
) {
    Primary(
        hex = "#00A2FF",
        rgb = rgb(r = 0, g = 162, b = 255)
    ),
    Secondary(
        hex = "#001019",
        rgb = rgb(r = 0, g = 16, b = 25)
    ),
    Tertiary(
        hex = "#001925",
        rgb = rgb(r = 0, g = 25, b = 37)
    ),
    LightGray(
        hex = "#FAFAFA",
        rgb = rgb(r = 250, g = 250, b = 250)
    ),
    Gray(
        hex = "#E9E9E9",
        rgb = rgb(r = 233, g = 233, b = 233)
    ),
    DarkGray(
        hex = "#646464",
        rgb = rgb(r = 100, g = 100, b = 100)
    ),
    HalfWhite(
        hex = "#FFFFFF",
        rgb = rgba(r = 255, g = 255, b = 255, a = 0.5f)
    ),
    White(
        hex = "#FFFFFF",
        rgb = rgb(r = 255, g = 255, b = 255)
    ),
    HalfBlack(
        hex = "#000000",
        rgb = rgba(r = 0, g = 0, b = 0, a = 0.5f)
    ),
    Green(
        hex = "#00FF94",
        rgb = rgb(r = 0, g = 255, b = 148)
    ),
    Yellow(
        hex = "#FFEC45",
        rgb = rgb(r = 255, g = 236, b = 69)
    ),
    Red(
        hex = "#FF6359",
        rgb = rgb(r = 255, g = 99, b = 89)
    ),
    Purple(
        hex = "#8B6DFF",
        rgb = rgb(r = 139, g = 109, b = 255)
    ),
    Sponsored(
        hex = "#3300FF",
        rgb = rgb(r = 51, g = 0, b = 255)
    )
}
/*
enum class YogaVedaTheme(
    val hex: String,
    val rgb: CSSColorValue
) {
    Orange(
        hex = "#D45700",
        rgb = rgb(r = 212, g = 87, b = 0)
    ),
    BlackGrey(
        hex = "#344033",
        rgb = rgb(r = 52, g = 64, b = 51)
    ),
    OrangeFaded(
        hex = "#FFDFCD",
        rgb = rgb(r = 255, g = 223, b = 205)
    ),
    BrownDull(
        hex = "#F2E9E3",
        rgb = rgb(242, 233, 227)
    ),
    White(
        hex = "#FFFFFF",
        rgb = rgb(255, 255, 255)
    ),
    Black(
        hex = "#000000",
        rgb = rgb(r = 0, g = 0, b = 0)
    ),
    Brown(
        hex = "#C1BAB5",
        rgb = rgb(193, 186, 181)
    ),
    BackgroundWhitish(
        hex = "#FAF9F7",
        rgb = rgb(250, 249, 247)
    )
}*/

object YogaVedaTheme {

    enum class Colors (
        val hex: String,
        val rgb: CSSColorValue
    ) {
        Orange(
            hex = "#D45700",
            rgb = rgb(r = 212, g = 87, b = 0)
        ),
        BlackGrey(
            hex = "#344033",
            rgb = rgb(r = 52, g = 64, b = 51)
        ),
        OrangeFaded(
            hex = "#FFDFCD",
            rgb = rgb(r = 255, g = 223, b = 205)
        ),
        BrownDull(
            hex = "#F2E9E3",
            rgb = rgb(242, 233, 227)
        ),
        White(
            hex = "#FFFFFF",
            rgb = rgb(255, 255, 255)
        ),
        Black(
            hex = "#000000",
            rgb = rgb(r = 0, g = 0, b = 0)
        ),
        Brown(
            hex = "#C1BAB5",
            rgb = rgb(193, 186, 181)
        ),
        BackgroundWhitish(
            hex = "#FAF9F7",
            rgb = rgb(250, 249, 247)
        )
    }

    enum class Fonts (
        val family: String,
    ) {
        Archivo (
            family = "Archivo"
        ),
        Gotu (
            family = "Gotu"
        ),
        LeagueScript (
            family = "League Script"
        ),
        Arial (
            family = "Arial"
        )
    }
}