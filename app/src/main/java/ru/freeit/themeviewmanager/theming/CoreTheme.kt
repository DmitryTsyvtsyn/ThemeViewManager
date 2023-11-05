package ru.freeit.themeviewmanager.theming

import ru.freeit.themeviewmanager.theming.colors.Colors
import ru.freeit.themeviewmanager.theming.shape.ShapeDrawableStrategy
import ru.freeit.themeviewmanager.theming.shape.Shapes
import ru.freeit.themeviewmanager.theming.typeface.Typefaces

enum class CoreTheme(
    val colors: Colors,
    val typefaces: Typefaces = Typefaces(
        title1 = "sf_pro_rounded_bold.ttf" to 23f,
        caption1 = "sf_pro_rounded_medium.ttf" to 17f,
        body1 = "sf_pro_rounded_regular.ttf" to 17f
    ),
    val shapes: Shapes = Shapes(
        button = ShapeDrawableStrategy.Rounded(8f, 8f, 8f, 8f),
        radioButton = ShapeDrawableStrategy.StrokeRounded(4, 12f, 12f, 12f, 12f)
    )
) {

    LIGHT(
        colors = Colors(
            primaryColor = CoreColors.greenMedium,
            primaryDarkColor = CoreColors.greenDark,
            primaryBackgroundColor = CoreColors.white,
            primaryTextColor = CoreColors.black,
            colorOnPrimary = CoreColors.white,
            disabledTextColor = CoreColors.grayMedium,
            disabledBackgroundColor = CoreColors.grayLight
        )
    ),

    DARK(
        colors = Colors(
            primaryColor = CoreColors.greenMedium,
            primaryDarkColor = CoreColors.greenDark,
            primaryBackgroundColor = CoreColors.black,
            primaryTextColor = CoreColors.white,
            colorOnPrimary = CoreColors.white,
            disabledTextColor = CoreColors.grayLight,
            disabledBackgroundColor = CoreColors.grayMedium
        )
    )

}