package ru.freeit.themeviewmanager.theming.shape

import ru.freeit.themeviewmanager.theming.CoreTheme
import kotlin.math.roundToInt

class Shapes(
    val button: ShapeDrawableStrategy,
    val radioButton: ShapeDrawableStrategy
) {

    fun currentProgress() : Int {
        val currentFactor = CoreTheme.defaultShapes.button.scaleFactor(button)
        return ((currentFactor - initial_factor) / (max_factor - initial_factor) * 100).roundToInt()
    }

    fun scaledByProgress(progress: Int) : Shapes {
        val factor = initial_factor + (progress / 100f) * (max_factor - initial_factor)
        return Shapes(
            button.scaled(factor),
            radioButton.scaled(factor)
        )
    }

    operator fun get(attribute: ShapeAttribute): ShapeDrawableStrategy {
        return when (attribute) {
            ShapeAttribute.None -> ShapeDrawableStrategy.None
            ShapeAttribute.Button -> button
            ShapeAttribute.RadioButton -> radioButton
            ShapeAttribute.Rounded -> ShapeDrawableStrategy.Rounded(50f, 50f, 50f, 50f)
        }
    }

    private companion object {
        const val initial_factor = 1f
        const val max_factor = 2f
    }

}