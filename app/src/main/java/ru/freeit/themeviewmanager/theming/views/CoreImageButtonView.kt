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
    private val shape: ShapeAttribute = ShapeAttribute.Maximum,
    private val rippleColor: ColorAttribute = ColorAttribute.PrimaryColor,
    private val backgroundColor: ColorAttribute = ColorAttribute.Transparent,
    tintColor: ColorAttribute = ColorAttribute.PrimaryTextColor
): CoreImageView(ctx, tintColor = tintColor) {

    init {
        isClickable = true
        isFocusable = true
    }

    override fun onThemeChanged(theme: CoreTheme) {
        super.onThemeChanged(theme)

        val rippleColor = ColorStateList.valueOf(theme.colors[rippleColor])

        val contentDrawable = theme.shapes[shape].drawable(context)
        contentDrawable.setTint(theme.colors[backgroundColor])

        val maskDrawable = theme.shapes[shape].drawable(context)
        maskDrawable.setTint(CoreColors.white.withAlpha(0.7f))

        background = RippleDrawable(rippleColor, contentDrawable, maskDrawable)
    }

}