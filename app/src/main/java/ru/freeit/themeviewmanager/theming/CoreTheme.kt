package ru.freeit.themeviewmanager.theming

import ru.freeit.themeviewmanager.theming.colors.Colors
import ru.freeit.themeviewmanager.theming.shape.ShapeDrawableStrategy
import ru.freeit.themeviewmanager.theming.shape.Shapes
import ru.freeit.themeviewmanager.theming.typeface.Typefaces

sealed class CoreTheme(
    val colors: Colors,
    val typefaces: Typefaces = Typefaces(
        title1 = "sf_pro_rounded_bold.ttf" to 23f,
        caption1 = "sf_pro_rounded_medium.ttf" to 17f,
        body1 = "sf_pro_rounded_regular.ttf" to 17f
    ),
    val shapes: Shapes = Shapes(
        button = ShapeDrawableStrategy.Rounded(8f, 8f, 8f, 8f),
        radioButton = ShapeDrawableStrategy.StrokeRounded(2, 12f, 12f, 12f, 12f)
    )
) {

    abstract fun custom(
        newColors: Colors = colors,
        newTypefaces: Typefaces = typefaces,
        newShapes: Shapes = shapes
    ) : CoreTheme

    class Light(
        colors: Colors = Colors(
            primaryColor = CoreColors.greenMedium,
            primaryDarkColor = CoreColors.greenDark,
            primaryBackgroundColor = CoreColors.white,
            primaryTextColor = CoreColors.black,
            colorOnPrimary = CoreColors.white,
            disabledTextColor = CoreColors.grayMedium,
            disabledBackgroundColor = CoreColors.grayLight
        )
    ) : CoreTheme(colors = colors) {
        override fun custom(
            newColors: Colors,
            newTypefaces: Typefaces,
            newShapes: Shapes
        ): CoreTheme = Light(colors = newColors)
    }

    class Dark(
        colors: Colors = Colors(
            primaryColor = CoreColors.greenMedium,
            primaryDarkColor = CoreColors.greenDark,
            primaryBackgroundColor = CoreColors.black,
            primaryTextColor = CoreColors.white,
            colorOnPrimary = CoreColors.white,
            disabledTextColor = CoreColors.grayMedium,
            disabledBackgroundColor = CoreColors.grayLight
        )
    ) : CoreTheme(colors = colors) {
        override fun custom(
            newColors: Colors,
            newTypefaces: Typefaces,
            newShapes: Shapes
        ): CoreTheme = Dark(colors = newColors)
    }

}