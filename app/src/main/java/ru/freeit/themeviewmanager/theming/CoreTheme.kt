package ru.freeit.themeviewmanager.theming

import ru.freeit.themeviewmanager.theming.colors.Colors


enum class CoreTheme(
    val colors: Colors
) {

    LIGHT(
        colors = Colors(
            primaryColor = CoreColors.greenMedium,
            primaryBackgroundColor = CoreColors.white,
            primaryTextColor = CoreColors.black
        )
    ),

    DARK(
        colors = Colors(
            primaryColor = CoreColors.greenMedium,
            primaryBackgroundColor = CoreColors.black,
            primaryTextColor = CoreColors.white
        )
    )

}