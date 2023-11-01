package ru.freeit.themeviewmanager.theming.shape

class Shapes(
    val small: ShapeDrawableStrategy
) {

    operator fun get(attribute: ShapeAttribute): ShapeDrawableStrategy {
        return when (attribute) {
            ShapeAttribute.none -> ShapeDrawableStrategy.None
            ShapeAttribute.small -> small
            ShapeAttribute.maximum -> ShapeDrawableStrategy.Rounded(50f, 50f, 50f, 50f)
        }
    }

}