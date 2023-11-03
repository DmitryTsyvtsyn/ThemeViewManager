package ru.freeit.themeviewmanager.theming.colors

import ru.freeit.themeviewmanager.theming.CoreColors

class Colors(
    private val primaryColor: Int,
    private val primaryDarkColor: Int,
    private val primaryBackgroundColor: Int,
    private val primaryTextColor: Int,
    private val colorOnPrimary: Int,
    private val disabledTextColor: Int,
    private val disabledBackgroundColor: Int
) {

    operator fun get(attribute: ColorAttribute): Int {
        return when(attribute) {
            is ColorAttribute.PrimaryColor -> primaryColor
            is ColorAttribute.PrimaryDarkColor -> primaryDarkColor
            is ColorAttribute.PrimaryBackgroundColor -> primaryBackgroundColor
            is ColorAttribute.PrimaryTextColor -> primaryTextColor
            is ColorAttribute.ColorOnPrimary -> colorOnPrimary
            is ColorAttribute.DisabledTextColor -> disabledTextColor
            is ColorAttribute.DisabledBackgroundColor -> disabledBackgroundColor
            is ColorAttribute.Transparent -> CoreColors.transparent
            is ColorAttribute.HardcodedColor -> attribute.color
        }
    }

}