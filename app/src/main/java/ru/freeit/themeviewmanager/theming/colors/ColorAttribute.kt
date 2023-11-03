package ru.freeit.themeviewmanager.theming.colors

sealed interface ColorAttribute {

    object PrimaryColor : ColorAttribute

    object PrimaryDarkColor : ColorAttribute

    object PrimaryBackgroundColor : ColorAttribute

    object PrimaryTextColor : ColorAttribute

    object ColorOnPrimary : ColorAttribute

    object DisabledTextColor : ColorAttribute

    object DisabledBackgroundColor : ColorAttribute

    object Transparent : ColorAttribute

    class HardcodedColor(val color: Int) : ColorAttribute

}