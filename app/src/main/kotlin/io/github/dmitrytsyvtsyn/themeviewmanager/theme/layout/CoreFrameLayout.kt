package io.github.dmitrytsyvtsyn.themeviewmanager.theme.layout

import android.content.Context
import android.widget.FrameLayout
import io.github.dmitrytsyvtsyn.themeviewmanager.theme.CoreTheme
import io.github.dmitrytsyvtsyn.themeviewmanager.theme.CoreThemeManager
import io.github.dmitrytsyvtsyn.themeviewmanager.theme.CoreThemeManagerProvider
import io.github.dmitrytsyvtsyn.themeviewmanager.theme.colors.ColorAttribute
import io.github.dmitrytsyvtsyn.themeviewmanager.theme.shape.ShapeAttribute

open class CoreFrameLayout @JvmOverloads constructor(
    ctx: Context,
    private val backgroundColor: ColorAttribute = ColorAttribute.PrimaryBackgroundColor,
    private val shape: ShapeAttribute = ShapeAttribute.None
): FrameLayout(ctx) {

    private fun onThemeChanged(theme: CoreTheme) {
        val backgroundDrawable = theme.shapes[shape].drawable(context)
        backgroundDrawable.setTint(theme.colors[backgroundColor])
        background = backgroundDrawable
    }

    protected val themeManager: CoreThemeManager

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

}