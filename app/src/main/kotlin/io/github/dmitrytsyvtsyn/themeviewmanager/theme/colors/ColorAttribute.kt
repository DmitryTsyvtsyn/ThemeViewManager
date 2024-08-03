package io.github.dmitrytsyvtsyn.themeviewmanager.theme.colors

sealed interface ColorAttribute {

    data object PrimaryColor : ColorAttribute

    data object PrimaryDarkColor : ColorAttribute

    data object PrimaryBackgroundColor : ColorAttribute

    data object PrimaryTextColor : ColorAttribute

    data object ColorOnPrimary : ColorAttribute

    data object DisabledTextColor : ColorAttribute

    data object DisabledBackgroundColor : ColorAttribute

    data object Transparent : ColorAttribute

    class HardcodedColor(val color: Int) : ColorAttribute

}