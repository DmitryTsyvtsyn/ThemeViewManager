package ru.freeit.themeviewmanager

import android.app.Application
import ru.freeit.themeviewmanager.theming.CoreThemeManager
import ru.freeit.themeviewmanager.theming.CoreThemeManagerProvider

class App : Application(), CoreThemeManagerProvider {

    private var themeManager: CoreThemeManager? = null

    override fun onCreate() {
        super.onCreate()

        themeManager = CoreThemeManager(assets)
    }

    override fun provide(): CoreThemeManager {
        return requireNotNull(themeManager)
    }

}