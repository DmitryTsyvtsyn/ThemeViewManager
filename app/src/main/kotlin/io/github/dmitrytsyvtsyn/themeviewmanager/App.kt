package io.github.dmitrytsyvtsyn.themeviewmanager

import android.app.Application
import io.github.dmitrytsyvtsyn.themeviewmanager.theme.CoreThemeManager
import io.github.dmitrytsyvtsyn.themeviewmanager.theme.CoreThemeManagerProvider

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