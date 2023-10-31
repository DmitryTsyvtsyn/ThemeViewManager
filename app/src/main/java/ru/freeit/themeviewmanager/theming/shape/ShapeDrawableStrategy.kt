package ru.freeit.themeviewmanager.theming.shape

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import androidx.annotation.Dimension
import ru.freeit.themeviewmanager.theming.extensions.dp

sealed interface ShapeDrawableStrategy {

    fun drawable(ctx: Context): Drawable

    data object None : ShapeDrawableStrategy {
        override fun drawable(ctx: Context): Drawable {
            return ShapeDrawable()
        }
    }

    class Rounded(
        @Dimension(unit = Dimension.DP) private val topStartRadius: Float = 0f,
        @Dimension(unit = Dimension.DP) private val topEndRadius: Float = 0f,
        @Dimension(unit = Dimension.DP) private val bottomEndRadius: Float = 0f,
        @Dimension(unit = Dimension.DP) private val bottomStartRadius: Float = 0f
    ) : ShapeDrawableStrategy {

        override fun drawable(ctx: Context): Drawable {
            val topStartRadiusInPixels = ctx.dp(topStartRadius)
            val topEndRadiusInPixels = ctx.dp(topEndRadius)
            val bottomEndRadiusInPixels = ctx.dp(bottomEndRadius)
            val bottomStartRadiusInPixels = ctx.dp(bottomStartRadius)

            return ShapeDrawable(
                RoundRectShape(
                    floatArrayOf(
                        topStartRadiusInPixels, topStartRadiusInPixels,
                        topEndRadiusInPixels, topEndRadiusInPixels,
                        bottomEndRadiusInPixels, bottomEndRadiusInPixels,
                        bottomStartRadiusInPixels, bottomStartRadiusInPixels
                    ), null, null
                )
            )
        }

    }

}