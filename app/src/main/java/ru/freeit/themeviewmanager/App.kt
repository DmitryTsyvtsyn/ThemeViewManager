package ru.freeit.themeviewmanager

import android.app.Application
import ru.freeit.themeviewmanager.theming.CoreThemeManager
import ru.freeit.themeviewmanager.theming.CoreThemeManagerProvider

class App : Application(), CoreThemeManagerProvider {

    private val themeManager = CoreThemeManager()

    override fun provide(): CoreThemeManager {
        return themeManager
    }

}