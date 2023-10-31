package ru.freeit.themeviewmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import ru.freeit.themeviewmanager.theming.CoreTheme
import ru.freeit.themeviewmanager.theming.CoreThemeManagerProvider
import ru.freeit.themeviewmanager.theming.layout.CoreFrameLayout
import ru.freeit.themeviewmanager.theming.layout.extensions.frameLayoutParams
import ru.freeit.themeviewmanager.theming.layout.extensions.layoutParams
import ru.freeit.themeviewmanager.theming.layout.extensions.viewGroupLayoutParams
import ru.freeit.themeviewmanager.theming.views.CoreTextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val contentView = CoreFrameLayout(this)
        val themeManager = (applicationContext as CoreThemeManagerProvider).provide()
        contentView.setOnClickListener {
            themeManager.toggleTheme()
            updateSystemBars(themeManager.selected_theme)
        }
        contentView.layoutParams(viewGroupLayoutParams().match())
        setContentView(contentView)

        val titleView = CoreTextView(this)
        titleView.text = "Today is the best day!"
        titleView.layoutParams(frameLayoutParams().wrap().gravity(Gravity.CENTER))
        contentView.addView(titleView)

        ViewCompat.setOnApplyWindowInsetsListener(contentView) { _, windowInsets ->
            val systemBarsInsets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            contentView.updatePadding(top = systemBarsInsets.top)

            WindowInsetsCompat.CONSUMED
        }

    }

    private fun updateSystemBars(theme: CoreTheme) {
        val isDarkMode = theme == CoreTheme.DARK
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = !isDarkMode
        insetsController.isAppearanceLightNavigationBars = !isDarkMode
    }

}