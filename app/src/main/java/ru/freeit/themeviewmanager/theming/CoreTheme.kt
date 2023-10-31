package ru.freeit.themeviewmanager.theming

import ru.freeit.themeviewmanager.theming.colors.Colors
import ru.freeit.themeviewmanager.theming.shape.ShapeDrawableStrategy
import ru.freeit.themeviewmanager.theming.shape.Shapes
import ru.freeit.themeviewmanager.theming.typeface.Typefaces

enum class CoreTheme(
    val colors: Colors,
    val typefaces: Typefaces = Typefaces(
        title1 = "sf_pro_rounded_medium.ttf" to 23f
    ),
    val shapes: Shapes = Shapes(
        small = ShapeDrawableStrategy.Rounded(8f, 8f, 8f, 8f)
    )
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