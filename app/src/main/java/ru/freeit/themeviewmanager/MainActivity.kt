package ru.freeit.themeviewmanager

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
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

    private var isThemeEditorScreenOpened = false

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
                is CoreTheme.Light -> R.drawable.ic_dark_mode
                is CoreTheme.Dark -> R.drawable.ic_light_mode
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

        fun openThemeEditorScreen(isAnimationEnabled: Boolean = true, hasEndedAnimation: () -> Unit = {}) {
            val bottomSheet = ThemeEditorScreen(this, isAnimationEnabled, hasEndedAnimation)
            bottomSheet.addOnAttachStateChangeListener(object: View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(v: View) {
                    isThemeEditorScreenOpened = true
                }

                override fun onViewDetachedFromWindow(v: View) {
                    isThemeEditorScreenOpened = false
                }
            })
            bottomSheet.layoutParams(frameLayoutParams().match())
            rootView.addView(bottomSheet)

            onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    rootView.removeView(bottomSheet)
                    isEnabled = false
                }
            })
        }

        val customizeThemeButton = CoreButton(this)
        customizeThemeButton.text = getString(R.string.customize_current_theme)
        customizeThemeButton.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginStart(dp(16))
            .marginEnd(dp(16)).marginBottom(dp(8)))
        customizeThemeButton.setOnClickListener {
            customizeThemeButton.isEnabled = false

            openThemeEditorScreen(hasEndedAnimation = {
                customizeThemeButton.isEnabled = true
            })
        }
        contentView.addView(customizeThemeButton)

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(theme_editor_screen_opened_key, false)) {
                openThemeEditorScreen(isAnimationEnabled = false)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(rootView) { _, windowInsets ->
            val systemBarsInsets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            rootView.updatePadding(top = systemBarsInsets.top, bottom = systemBarsInsets.bottom)

            WindowInsetsCompat.CONSUMED
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(theme_editor_screen_opened_key, isThemeEditorScreenOpened)
    }

    private fun updateSystemBars(theme: CoreTheme) {
        val isDarkMode = theme is CoreTheme.Dark
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = !isDarkMode
        insetsController.isAppearanceLightNavigationBars = !isDarkMode
    }

    private companion object {
        const val theme_editor_screen_opened_key = "theme_editor_screen_opened_key"
    }

}