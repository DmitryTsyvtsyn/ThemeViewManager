package ru.freeit.themeviewmanager.theming.views

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import ru.freeit.themeviewmanager.theming.CoreTheme
import ru.freeit.themeviewmanager.theming.CoreThemeManager
import ru.freeit.themeviewmanager.theming.CoreThemeManagerProvider
import ru.freeit.themeviewmanager.theming.colors.ColorAttribute

open class CoreImageView @JvmOverloads constructor(
    ctx: Context,
    private var tintColor: ColorAttribute = ColorAttribute.PrimaryTextColor
): AppCompatImageView(ctx) {

    private val themeManager: CoreThemeManager

    init {
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
        if (tintColor is ColorAttribute.Transparent) return

        setColorFilter(theme.colors[tintColor])
    }

    fun changeTint(color: ColorAttribute) {
        if (color is ColorAttribute.Transparent) return

        tintColor = color
        setColorFilter(themeManager.selected_theme.colors[color])
    }

}