package ru.freeit.themeviewmanager.theming.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import ru.freeit.themeviewmanager.theming.CoreColors
import ru.freeit.themeviewmanager.theming.CoreTheme
import ru.freeit.themeviewmanager.theming.colors.ColorAttribute
import ru.freeit.themeviewmanager.theming.extensions.withAlpha
import ru.freeit.themeviewmanager.theming.shape.ShapeAttribute

class CoreImageButtonView @JvmOverloads constructor(
    ctx: Context,
    private val shape: ShapeAttribute = ShapeAttribute.maximum,
    private val rippleColor: ColorAttribute = ColorAttribute.primaryColor,
    private val backgroundColor: ColorAttribute? = null,
    tintColor: ColorAttribute = ColorAttribute.primaryTextColor
): CoreImageView(ctx, tintColor = tintColor) {

    init {
        isClickable = true
        isFocusable = true
    }

    override fun onThemeChanged(theme: CoreTheme) {
        super.onThemeChanged(theme)

        val rippleColor = ColorStateList.valueOf(theme.colors[rippleColor])

        val contentDrawable = if (backgroundColor != null) {
            val drawable = theme.shapes[shape].drawable(context)
            drawable.setTint(theme.colors[backgroundColor])
            drawable
        } else {
            null
        }

        val maskDrawable = theme.shapes[shape].drawable(context)
        maskDrawable.setTint(CoreColors.white.withAlpha(0.7f))

        background = RippleDrawable(rippleColor, contentDrawable, maskDrawable)
    }

}