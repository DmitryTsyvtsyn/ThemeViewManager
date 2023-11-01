package ru.freeit.themeviewmanager.theming.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.view.Gravity
import ru.freeit.themeviewmanager.theming.CoreTheme
import ru.freeit.themeviewmanager.theming.colors.ColorAttribute
import ru.freeit.themeviewmanager.theming.extensions.dp
import ru.freeit.themeviewmanager.theming.extensions.padding
import ru.freeit.themeviewmanager.theming.shape.ShapeAttribute
import ru.freeit.themeviewmanager.theming.typeface.TypefaceAttribute

class CoreButton @JvmOverloads constructor(
    ctx: Context,
    private val shape: ShapeAttribute = ShapeAttribute.small,
    private val backgroundColor: ColorAttribute = ColorAttribute.primaryColor,
    private val rippleColor: ColorAttribute = ColorAttribute.primaryDarkColor,
    textColor: ColorAttribute = ColorAttribute.colorOnPrimary,
    typeface: TypefaceAttribute = TypefaceAttribute.Caption1
) : CoreTextView(ctx, textColor = textColor, typeface = typeface) {

    init {
        isClickable = true
        isFocusable = true
        gravity = Gravity.CENTER
        padding(horizontal = context.dp(8), vertical = context.dp(12))
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        themeManager.listenForThemeChanges(::onThemeChanged)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        themeManager.doNotListenForThemeChanges(::onThemeChanged)
    }

    override fun onThemeChanged(theme: CoreTheme) {
        super.onThemeChanged(theme)

        val contentDrawable = theme.shapes[shape].drawable(context)
        contentDrawable.setTint(theme.colors[backgroundColor])

        val color = ColorStateList.valueOf(theme.colors[rippleColor])

        background = RippleDrawable(color, contentDrawable, null)
    }

}