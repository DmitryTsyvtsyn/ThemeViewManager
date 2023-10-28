package ru.freeit.themeviewmanager.theming

class CoreThemeManager {

    private val listeners = mutableListOf<(CoreTheme) -> Unit>()

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

}