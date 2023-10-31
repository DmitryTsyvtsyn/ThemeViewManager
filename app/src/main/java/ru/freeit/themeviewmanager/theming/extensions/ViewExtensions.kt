package ru.freeit.themeviewmanager.theming.extensions

import android.content.Context
import kotlin.math.roundToInt

fun Context.dp(dimen: Int) = (resources.displayMetrics.density * dimen).roundToInt()

fun Context.dp(dimen: Float) = (resources.displayMetrics.density * dimen)