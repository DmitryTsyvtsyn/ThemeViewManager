package ru.freeit.themeviewmanager.theming.views

import android.content.Context
import android.view.Gravity
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import ru.freeit.themeviewmanager.R
import ru.freeit.themeviewmanager.theming.extensions.dp
import ru.freeit.themeviewmanager.theming.extensions.frameLayoutParams
import ru.freeit.themeviewmanager.theming.extensions.layoutParams
import ru.freeit.themeviewmanager.theming.extensions.padding
import ru.freeit.themeviewmanager.theming.layout.CoreFrameLayout

class CoreToolbarView(ctx: Context) : CoreFrameLayout(ctx) {

    private val backButtonView = CoreImageButtonView(context)
    private val toolbarTitleView = CoreTextView(context)
    private val menuButtonView = CoreImageButtonView(context)

    init {
        val buttonSize = 40
        val buttonMargin = 8

        backButtonView.isVisible = false
        backButtonView.padding(context.dp(8))
        backButtonView.setImageResource(R.drawable.ic_back)
        backButtonView.layoutParams(
            frameLayoutParams().width(context.dp(buttonSize)).height(context.dp(buttonSize))
            .gravity(Gravity.START).marginStart(context.dp(buttonMargin)))
        addView(backButtonView)

        val toolbarTitleMargin = buttonSize + buttonMargin * 2

        toolbarTitleView.layoutParams(frameLayoutParams().wrap().gravity(Gravity.CENTER).marginStart(toolbarTitleMargin).marginEnd(toolbarTitleMargin))
        addView(toolbarTitleView)

        menuButtonView.isVisible = false
        menuButtonView.padding(context.dp(8))
        menuButtonView.layoutParams(
            frameLayoutParams().width(context.dp(buttonSize)).height(context.dp(buttonSize))
            .gravity(Gravity.END).marginEnd(context.dp(buttonMargin)))
        addView(menuButtonView)
    }

    fun setOnBackClickListener(listener: OnClickListener) {
        backButtonView.isVisible = true
        backButtonView.setOnClickListener(listener)
    }

    fun setOnMenuClickListener(listener: OnClickListener) {
        menuButtonView.isVisible = true
        menuButtonView.setOnClickListener(listener)
    }

    fun changeTitle(title: String) {
        toolbarTitleView.text = title
    }

    fun changeMenuImageResource(@DrawableRes resource: Int) {
        menuButtonView.setImageResource(resource)
    }

}