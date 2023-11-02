package ru.freeit.themeviewmanager.theming

import android.content.res.AssetManager
import android.graphics.Typeface

class CoreThemeManager(private val assetManager: AssetManager) {

    private val listeners = mutableListOf<(CoreTheme) -> Unit>()

    private val cachedTypefaces = hashMapOf<String, Typeface>()

    private var currentTheme = CoreTheme.LIGHT

    val selected_theme: CoreTheme
        get() = currentTheme

    fun listenForThemeChanges(listener: (CoreTheme) -> Unit) {
        listeners.add(listener)
        listener.invoke(currentTheme)
    }

    fun doNotListenForThemeChanges(listener: (CoreTheme) -> Unit) {
        listeners.remove(listener)
    }

    fun toggleTheme() {
        currentTheme = if (currentTheme == CoreTheme.LIGHT) CoreTheme.DARK else CoreTheme.LIGHT
        listeners.forEach { listener -> listener.invoke(currentTheme) }
    }

    fun typeface(path: String): Typeface {
        val cachedTypeface = cachedTypefaces[path]

        if (cachedTypeface != null) {
            return cachedTypeface
        }

        val typeface = Typeface.createFromAsset(assetManager, path)
        cachedTypefaces[path] = typeface
        return typeface
    }

}