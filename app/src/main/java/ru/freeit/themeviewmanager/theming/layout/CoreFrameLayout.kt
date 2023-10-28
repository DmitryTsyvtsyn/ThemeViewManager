package ru.freeit.themeviewmanager.theming.layout

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.widget.FrameLayout
import ru.freeit.themeviewmanager.theming.CoreTheme
import ru.freeit.themeviewmanager.theming.CoreThemeManager
import ru.freeit.themeviewmanager.theming.CoreThemeManagerProvider
import ru.freeit.themeviewmanager.theming.colors.ColorAttribute

open class CoreFrameLayout @JvmOverloads constructor(
    ctx: Context,
    private val backgroundColor: ColorAttribute = ColorAttribute.primaryBackgroundColor
): FrameLayout(ctx) {

    private val onThemeChanged: (CoreTheme) -> Unit = { theme ->
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(theme.colors[backgroundColor])
        background = gradientDrawable
    }

    private val themeManager: CoreThemeManager

    init {
        require(context.applicationContext is CoreThemeManagerProvider) {
            "Your Application class must to implement CoreThemeManagerProvider interface"
        }

        themeManager = (context.applicationContext as CoreThemeManagerProvider).provide()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        themeManager.listenForThemeChanges(onThemeChanged)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        themeManager.doNotListenForThemeChanges(onThemeChanged)
    }

}