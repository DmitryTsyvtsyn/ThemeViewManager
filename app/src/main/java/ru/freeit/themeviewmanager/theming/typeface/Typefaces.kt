package ru.freeit.themeviewmanager.theming.typeface

import ru.freeit.themeviewmanager.theming.CoreTheme
import kotlin.math.roundToInt

class Typefaces(
    private val title1: Pair<String, Float>,
    private val caption1: Pair<String, Float>,
    private val body1: Pair<String, Float>
) {

    fun currentProgress(): Int {
        val currentFontSize = title1.second
        val initialFontSize = CoreTheme.defaultTypefaces.title1.second
        val maxFontSize = CoreTheme.defaultTypefaces.title1.second * max_factor

        return ((currentFontSize - initialFontSize) / (maxFontSize - initialFontSize) * 100).roundToInt()
    }

    fun scaledByProgress(progress: Int): Typefaces {
        val factor = progress / 100f
        return Typefaces(
            title1 = title1.copy(second = title1.scaledFontSize(factor)),
            caption1 = caption1.copy(second = caption1.scaledFontSize(factor)),
            body1 = body1.copy(second = body1.scaledFontSize(factor))
        )
    }

    private fun Pair<String, Float>.scaledFontSize(factor: Float): Float {
        val currentFontSize = second
        val maxFontSize = second * max_factor

        return currentFontSize + (maxFontSize - currentFontSize) * factor
    }

    operator fun get(attr: TypefaceAttribute): Pair<String, Float> {
        return when (attr) {
            TypefaceAttribute.Title1 -> title1
            TypefaceAttribute.Caption1 -> caption1
            TypefaceAttribute.Body1 -> body1
        }
    }

    private companion object {
        const val max_factor = 1.5f
    }

}