package ru.freeit.themeviewmanager.theming.shape

class Shapes(
    val small: ShapeDrawableStrategy
) {

    operator fun get(attribute: ShapeAttribute): ShapeDrawableStrategy {
        return when (attribute) {
            ShapeAttribute.None -> ShapeDrawableStrategy.None
            ShapeAttribute.Small -> small
            ShapeAttribute.Maximum -> ShapeDrawableStrategy.Rounded(50f, 50f, 50f, 50f)
        }
    }

}