package ru.freeit.themeviewmanager.theming.typeface

class Typefaces(
    private val title1: Pair<String, Float>,
    private val caption1: Pair<String, Float>,
    private val body1: Pair<String, Float>
) {

    operator fun get(attr: TypefaceAttribute): Pair<String, Float> {
        return when (attr) {
            TypefaceAttribute.Title1 -> title1
            TypefaceAttribute.Caption1 -> caption1
            TypefaceAttribute.Body1 -> body1
        }
    }

}