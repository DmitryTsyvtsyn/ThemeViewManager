package ru.freeit.themeviewmanager.theming.views

import android.content.Context
import android.content.res.ColorStateList
import androidx.appcompat.widget.AppCompatSeekBar
import ru.freeit.themeviewmanager.theming.CoreTheme
import ru.freeit.themeviewmanager.theming.CoreThemeManager
import ru.freeit.themeviewmanager.theming.CoreThemeManagerProvider
import ru.freeit.themeviewmanager.theming.colors.ColorAttribute
import ru.freeit.themeviewmanager.theming.extensions.dp
import ru.freeit.themeviewmanager.theming.extensions.padding

class CoreSeekBar @JvmOverloads constructor(
    ctx: Context,
    private val tintColor: ColorAttribute = ColorAttribute.PrimaryColor
) : AppCompatSeekBar(ctx) {

    private val themeManager: CoreThemeManager

    init {
        padding(context.dp(12))

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

    private fun onThemeChanged(theme: CoreTheme) {
        progressTintList = ColorStateList.valueOf(theme.colors[tintColor])
        thumbTintList = ColorStateList.valueOf(theme.colors[tintColor])
    }

}