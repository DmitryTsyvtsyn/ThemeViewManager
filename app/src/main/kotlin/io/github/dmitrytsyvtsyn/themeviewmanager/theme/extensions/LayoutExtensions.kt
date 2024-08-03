package io.github.dmitrytsyvtsyn.themeviewmanager.theme.extensions

import android.view.View
import io.github.dmitrytsyvtsyn.themeviewmanager.theme.layout.params.AbstractLP
import io.github.dmitrytsyvtsyn.themeviewmanager.theme.layout.params.FrameLayoutLP
import io.github.dmitrytsyvtsyn.themeviewmanager.theme.layout.params.LinearLayoutLP
import io.github.dmitrytsyvtsyn.themeviewmanager.theme.layout.params.ViewGroupLP

fun linearLayoutParams() = LinearLayoutLP()
fun frameLayoutParams() = FrameLayoutLP()
fun viewGroupLayoutParams() = ViewGroupLP()

fun View.layoutParams(params: AbstractLP<*, *>) {
    layoutParams = params.build()
}