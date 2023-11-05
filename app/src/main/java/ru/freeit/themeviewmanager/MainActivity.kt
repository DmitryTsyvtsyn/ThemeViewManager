package ru.freeit.themeviewmanager

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import ru.freeit.themeviewmanager.theming.CoreTheme
import ru.freeit.themeviewmanager.theming.CoreThemeManagerProvider
import ru.freeit.themeviewmanager.theming.extensions.dp
import ru.freeit.themeviewmanager.theming.extensions.frameLayoutParams
import ru.freeit.themeviewmanager.theming.extensions.layoutParams
import ru.freeit.themeviewmanager.theming.extensions.linearLayoutParams
import ru.freeit.themeviewmanager.theming.extensions.viewGroupLayoutParams
import ru.freeit.themeviewmanager.theming.layout.CoreFrameLayout
import ru.freeit.themeviewmanager.theming.layout.CoreLinearLayout
import ru.freeit.themeviewmanager.theming.typeface.TypefaceAttribute
import ru.freeit.themeviewmanager.theming.views.CoreButton
import ru.freeit.themeviewmanager.theming.views.CoreTextView
import ru.freeit.themeviewmanager.theming.views.CoreToolbarView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val rootView = CoreFrameLayout(this)
        rootView.layoutParams(viewGroupLayoutParams().match())

        val contentView = CoreLinearLayout(this)
        contentView.orientation = LinearLayout.VERTICAL
        contentView.layoutParams(frameLayoutParams().match())
        rootView.addView(contentView)
        setContentView(rootView)

        val toolbarView = CoreToolbarView(this)
        toolbarView.changeTitle(getString(R.string.app_name))
        toolbarView.layoutParams(linearLayoutParams().matchWidth().wrapHeight())
        contentView.addView(toolbarView)

        val themeManager = (applicationContext as CoreThemeManagerProvider).provide()

        fun updateTheme() {
            val currentTheme = themeManager.selected_theme
            updateSystemBars(currentTheme)
            val drawableResource = when(currentTheme) {
                CoreTheme.LIGHT -> R.drawable.ic_dark_mode
                CoreTheme.DARK -> R.drawable.ic_light_mode
            }
            if (drawableResource > 0) toolbarView.changeMenuImageResource(drawableResource)
        }

        updateTheme()

        toolbarView.setOnMenuClickListener {
            themeManager.toggleTheme()
            updateTheme()
        }

        val contentTextView = CoreTextView(this, typeface = TypefaceAttribute.Body1)
        contentTextView.setText(R.string.repository_description)
        contentTextView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().margins(dp(16)))
        contentView.addView(contentTextView)

        val spacerView = View(this)
        spacerView.layoutParams(linearLayoutParams().matchWidth().height(0).weight(1f))
        contentView.addView(spacerView)

        val contentButtonView = CoreButton(this)
        contentButtonView.setText(R.string.read_more)
        contentButtonView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().margins(dp(16)))
        contentButtonView.setOnClickListener {
            contentButtonView.isEnabled = false
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://github.com/evitwilly/ThemeViewManager")
                startActivity(intent)
            } catch(_: Exception) {

            } finally {
                contentButtonView.isEnabled = true
            }
        }
        contentView.addView(contentButtonView)

        ViewCompat.setOnApplyWindowInsetsListener(rootView) { _, windowInsets ->
            val systemBarsInsets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            rootView.updatePadding(top = systemBarsInsets.top, bottom = systemBarsInsets.bottom)

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