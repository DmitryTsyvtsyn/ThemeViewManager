package ru.freeit.themeviewmanager.theming.views

import android.content.Context
import android.content.res.ColorStateList
import androidx.appcompat.widget.AppCompatTextView
import ru.freeit.themeviewmanager.theming.CoreTheme
import ru.freeit.themeviewmanager.theming.CoreThemeManager
import ru.freeit.themeviewmanager.theming.CoreThemeManagerProvider
import ru.freeit.themeviewmanager.theming.colors.ColorAttribute
import ru.freeit.themeviewmanager.theming.typeface.TypefaceAttribute

open class CoreTextView @JvmOverloads constructor(
    ctx: Context,
    private var textColor: ColorAttribute = ColorAttribute.PrimaryTextColor,
    private val disabledTextColor: ColorAttribute = ColorAttribute.DisabledTextColor,
    private var typeface: TypefaceAttribute = TypefaceAttribute.Title1
): AppCompatTextView(ctx) {

    private var cachedFontSize: Float = -1f

    protected val themeManager: CoreThemeManager

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

    protected open fun onThemeChanged(theme: CoreTheme) {
        changeTextColor(textColor)
        changeTypeface(typeface)
    }

    fun changeTextColor(color: ColorAttribute) {
        val currentTheme = themeManager.selected_theme
        textColor = color
        setTextColor(ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_enabled)
            ),
            intArrayOf(
                currentTheme.colors[disabledTextColor],
                currentTheme.colors[color]
            )
        ))
    }

    fun changeTypeface(typeface: TypefaceAttribute) {
        this.typeface = typeface
        val (typefacePath, fontSize) = themeManager.selected_theme.typefaces[typeface]
        setTypeface(themeManager.typeface(typefacePath))

        super.setTextSize(if (cachedFontSize > 0) cachedFontSize else fontSize)
    }

    override fun setTextSize(size: Float) {
        cachedFontSize = size
        super.setTextSize(size)
    }

}