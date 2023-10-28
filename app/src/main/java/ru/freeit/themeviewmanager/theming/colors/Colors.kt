package ru.freeit.themeviewmanager.theming.colors

class Colors(
    private val primaryColor: Int,
    private val primaryBackgroundColor: Int,
    private val primaryTextColor: Int
) {

    operator fun get(type: ColorAttribute): Int {
        return when(type) {
            ColorAttribute.primaryColor -> primaryColor
            ColorAttribute.primaryBackgroundColor -> primaryBackgroundColor
            ColorAttribute.primaryTextColor -> primaryTextColor
        }
    }

}