package ru.freeit.themeviewmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import ru.freeit.themeviewmanager.theming.CoreThemeManagerProvider
import ru.freeit.themeviewmanager.theming.layout.CoreFrameLayout
import ru.freeit.themeviewmanager.theming.layout.extensions.frameLayoutParams
import ru.freeit.themeviewmanager.theming.layout.extensions.layoutParams
import ru.freeit.themeviewmanager.theming.layout.extensions.viewGroupLayoutParams
import ru.freeit.themeviewmanager.theming.views.CoreTextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contentView = CoreFrameLayout(this)
        val themeManager = (applicationContext as CoreThemeManagerProvider).provide()
        contentView.setOnClickListener {
            themeManager.toggleTheme()
        }
        contentView.layoutParams(viewGroupLayoutParams().match())
        setContentView(contentView)

        val titleView = CoreTextView(this)
        titleView.text = "Today is the best day!"
        titleView.layoutParams(frameLayoutParams().wrap().gravity(Gravity.CENTER))
        contentView.addView(titleView)

    }

}