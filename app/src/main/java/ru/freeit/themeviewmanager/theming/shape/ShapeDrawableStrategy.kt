package ru.freeit.themeviewmanager.theming.shape

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import androidx.annotation.Dimension
import ru.freeit.themeviewmanager.theming.extensions.dp

sealed interface ShapeDrawableStrategy {

    fun drawable(ctx: Context): Drawable

    fun scaled(factor: Float): ShapeDrawableStrategy

    fun scaleFactor(other: ShapeDrawableStrategy): Float

    data object None : ShapeDrawableStrategy {

        override fun drawable(ctx: Context): Drawable {
            return ShapeDrawable()
        }

        override fun scaled(factor: Float): ShapeDrawableStrategy {
            return this
        }

        override fun scaleFactor(other: ShapeDrawableStrategy): Float {
            return 1f
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

        override fun scaled(factor: Float): ShapeDrawableStrategy {
            return Rounded(
                topStartRadius * factor,
                topEndRadius * factor,
                bottomEndRadius * factor,
                bottomStartRadius * factor
            )
        }

        override fun scaleFactor(other: ShapeDrawableStrategy): Float {
            if (other is Rounded) {
                return other.topStartRadius / topStartRadius
            }
            return 1f

        }
    }

    class StrokeRounded(
        @Dimension(unit = Dimension.DP) private val strokeWidth: Int = 0,
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

            return object: GradientDrawable() {
                override fun setTint(tintColor: Int) {
                    setStroke(ctx.dp(strokeWidth), tintColor)
                }
            }.apply {
                cornerRadii = floatArrayOf(
                    topStartRadiusInPixels, topStartRadiusInPixels,
                    topEndRadiusInPixels, topEndRadiusInPixels,
                    bottomEndRadiusInPixels, bottomEndRadiusInPixels,
                    bottomStartRadiusInPixels, bottomStartRadiusInPixels
                )
            }
        }

        override fun scaled(factor: Float): ShapeDrawableStrategy {
            return StrokeRounded(
                strokeWidth,
                topStartRadius * factor,
                topEndRadius * factor,
                bottomEndRadius * factor,
                bottomStartRadius * factor
            )
        }

        override fun scaleFactor(other: ShapeDrawableStrategy): Float {
            if (other is StrokeRounded) {
                return other.topStartRadius / topStartRadius
            }
            return 1f
        }
    }

}