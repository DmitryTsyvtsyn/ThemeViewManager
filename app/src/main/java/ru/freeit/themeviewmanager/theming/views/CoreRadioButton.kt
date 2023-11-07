package ru.freeit.themeviewmanager.theming.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.InsetDrawable
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.widget.CompoundButtonCompat
import ru.freeit.themeviewmanager.theming.CoreTheme
import ru.freeit.themeviewmanager.theming.CoreThemeManager
import ru.freeit.themeviewmanager.theming.CoreThemeManagerProvider
import ru.freeit.themeviewmanager.theming.colors.ColorAttribute
import ru.freeit.themeviewmanager.theming.extensions.dp
import ru.freeit.themeviewmanager.theming.shape.ShapeAttribute
import ru.freeit.themeviewmanager.theming.typeface.TypefaceAttribute

class CoreRadioButton @JvmOverloads constructor(
    ctx: Context,
    private val textColor: ColorAttribute = ColorAttribute.PrimaryTextColor,
    private val disabledTextColor: ColorAttribute = ColorAttribute.DisabledTextColor,
    private val typeface: TypefaceAttribute = TypefaceAttribute.Title1,
    private val shape: ShapeAttribute = ShapeAttribute.RadioButton,
    private val tintColor: ColorAttribute = ColorAttribute.PrimaryColor
) : AppCompatRadioButton(ctx) {

    private val themeManager: CoreThemeManager

    init {
        includeFontPadding = false

        require(context.applicationContext is CoreThemeManagerProvider) {
            "Your Application class must to implement CoreThemeManagerProvider interface"
        }

        themeManager = (context.applicationContext as CoreThemeManagerProvider).provide()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        themeManager.listenForThemeChanges(::onThemeChanged)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        themeManager.doNotListenForThemeChanges(::onThemeChanged)
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(0, top, right, bottom)

        val drawable = CompoundButtonCompat.getButtonDrawable(this)
        if (drawable != null) {
            buttonDrawable = InsetDrawable(drawable, left, top, context.dp(textMarginStart), bottom)
            compoundDrawablePadding = 0
        }
    }

    private fun onThemeChanged(theme: CoreTheme) {
        val (typefacePath, fontSize) = theme.typefaces[typeface]

        setTextColor(ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_enabled)
            ),
            intArrayOf(
                theme.colors[disabledTextColor],
                theme.colors[textColor]
            )
        ))
        setTextSize(fontSize)
        setTypeface(themeManager.typeface(typefacePath))

        val tintColor = theme.colors[tintColor]
        buttonTintList = ColorStateList.valueOf(tintColor)

        val backgroundDrawable = theme.shapes[shape].drawable(context)
        backgroundDrawable.setTint(tintColor)
        background = backgroundDrawable
    }

    private companion object {
        const val textMarginStart = 8
    }

}