package ru.freeit.themeviewmanager.theming.views

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import ru.freeit.themeviewmanager.theming.CoreTheme
import ru.freeit.themeviewmanager.theming.CoreThemeManager
import ru.freeit.themeviewmanager.theming.CoreThemeManagerProvider
import ru.freeit.themeviewmanager.theming.colors.ColorAttribute
import ru.freeit.themeviewmanager.theming.typeface.TypefaceAttribute

open class CoreTextView @JvmOverloads constructor(
    ctx: Context,
    private var textColor: ColorAttribute = ColorAttribute.primaryTextColor,
    private var typeface: TypefaceAttribute = TypefaceAttribute.Title1
): AppCompatTextView(ctx) {

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

    protected open fun onThemeChanged(theme: CoreTheme) {
        setTextColor(theme.colors[textColor])

        changeTypeface(typeface)
    }

    fun changeTextColor(color: ColorAttribute) {
        textColor = color
        setTextColor(themeManager.selected_theme.colors[color])
    }

    fun changeTypeface(typeface: TypefaceAttribute) {
        val (typefacePath, fontSize) = themeManager.selected_theme.typefaces[typeface]
        setTypeface(themeManager.typeface(typefacePath))
        setTextSize(fontSize)
    }

}