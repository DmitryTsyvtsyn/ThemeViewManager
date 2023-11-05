package ru.freeit.themeviewmanager.theming.shape

class Shapes(
    val button: ShapeDrawableStrategy,
    val radioButton: ShapeDrawableStrategy
) {

    operator fun get(attribute: ShapeAttribute): ShapeDrawableStrategy {
        return when (attribute) {
            ShapeAttribute.None -> ShapeDrawableStrategy.None
            ShapeAttribute.Button -> button
            ShapeAttribute.RadioButton -> radioButton
            ShapeAttribute.Rounded -> ShapeDrawableStrategy.Rounded(50f, 50f, 50f, 50f)
        }
    }

}