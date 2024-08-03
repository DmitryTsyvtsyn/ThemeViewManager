package io.github.dmitrytsyvtsyn.themeviewmanager.theme

interface CoreThemeManagerProvider {
    fun provide() : CoreThemeManager
}