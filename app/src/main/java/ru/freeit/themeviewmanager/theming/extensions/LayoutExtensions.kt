package ru.freeit.themeviewmanager.theming.extensions

import android.view.View
import ru.freeit.themeviewmanager.theming.layout.params.AbstractLP
import ru.freeit.themeviewmanager.theming.layout.params.FrameLayoutLP
import ru.freeit.themeviewmanager.theming.layout.params.LinearLayoutLP
import ru.freeit.themeviewmanager.theming.layout.params.ViewGroupLP

fun linearLayoutParams() = LinearLayoutLP()
fun frameLayoutParams() = FrameLayoutLP()
fun viewGroupLayoutParams() = ViewGroupLP()

fun View.layoutParams(params: AbstractLP<*, *>) {
    layoutParams = params.build()
}