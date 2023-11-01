package ru.freeit.themeviewmanager.theming.colors

class Colors(
    private val primaryColor: Int,
    private val primaryDarkColor: Int,
    private val primaryBackgroundColor: Int,
    private val primaryTextColor: Int,
    private val colorOnPrimary: Int
) {

    operator fun get(type: ColorAttribute): Int {
        return when(type) {
            ColorAttribute.primaryColor -> primaryColor
            ColorAttribute.primaryDarkColor -> primaryDarkColor
            ColorAttribute.primaryBackgroundColor -> primaryBackgroundColor
            ColorAttribute.primaryTextColor -> primaryTextColor
            ColorAttribute.colorOnPrimary -> colorOnPrimary
        }
    }

}