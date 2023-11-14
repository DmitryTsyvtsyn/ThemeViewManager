package ru.freeit.themeviewmanager

import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.ScrollView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.animation.doOnEnd
import ru.freeit.themeviewmanager.theming.CoreColors
import ru.freeit.themeviewmanager.theming.CoreTheme
import ru.freeit.themeviewmanager.theming.colors.ColorAttribute
import ru.freeit.themeviewmanager.theming.extensions.dp
import ru.freeit.themeviewmanager.theming.extensions.layoutParams
import ru.freeit.themeviewmanager.theming.extensions.linearLayoutParams
import ru.freeit.themeviewmanager.theming.extensions.padding
import ru.freeit.themeviewmanager.theming.extensions.viewGroupLayoutParams
import ru.freeit.themeviewmanager.theming.layout.CoreLinearLayout
import ru.freeit.themeviewmanager.theming.typeface.TypefaceAttribute
import ru.freeit.themeviewmanager.theming.views.CoreRadioButton
import ru.freeit.themeviewmanager.theming.views.CoreSeekBar
import ru.freeit.themeviewmanager.theming.views.CoreTextView
import ru.freeit.themeviewmanager.theming.views.CoreToolbarView

class ThemeEditorScreen(
    ctx: Context,
    private val isAnimationEnabled: Boolean = true,
    private val hasEndedAnimation: () -> Unit = {}
) : CoreLinearLayout(
    ctx = ctx,
    backgroundColor = ColorAttribute.PrimaryBackgroundColor
) {

    init {
        orientation = VERTICAL

        val toolbarView = CoreToolbarView(context)
        toolbarView.layoutParams(linearLayoutParams().matchWidth().wrapHeight())
        toolbarView.changeTitle(context.getString(R.string.theme_customizing))
        toolbarView.setOnBackClickListener {
            val parentLayout = (parent as? ViewGroup)
            if (parentLayout != null) {
                val hasContains = parentLayout.indexOfChild(this) != -1
                if (hasContains) {
                    parentLayout.removeView(this)
                }
            }
        }
        addView(toolbarView)

        val scrollView = ScrollView(context)
        scrollView.layoutParams(linearLayoutParams().matchWidth().wrapHeight())
        addView(scrollView)

        val contentView = CoreLinearLayout(context)
        contentView.orientation = VERTICAL
        contentView.padding(start = context.dp(16), end = context.dp(16))
        contentView.layoutParams(viewGroupLayoutParams().matchWidth().wrapHeight())
        scrollView.addView(contentView)

        contentView.addThemeChangingViewsForColors(
            "Primary color:",
            themeManager.selected_theme.colors[ColorAttribute.PrimaryColor],
            listOf(
                "green" to CoreColors.greenMedium,
                "red" to CoreColors.redMedium,
                "purple" to CoreColors.purpleMedium
            )
        ) { newPrimaryColor ->
            val currentTheme = themeManager.selected_theme
            themeManager.changeTheme(currentTheme.custom(
                newColors = currentTheme.colors.copy(
                    primaryColor = newPrimaryColor
                )
            ))
        }

        contentView.addThemeChangingViewsForColors(
            "Primary dark color:",
            themeManager.selected_theme.colors[ColorAttribute.PrimaryDarkColor],
            listOf(
                "green dark" to CoreColors.greenDark,
                "red dark" to CoreColors.redDark,
                "purple dark" to CoreColors.purpleDark
            )
        ) { newPrimaryDarkColor ->
            val currentTheme = themeManager.selected_theme
            themeManager.changeTheme(currentTheme.custom(
                newColors = currentTheme.colors.copy(
                    primaryDarkColor = newPrimaryDarkColor
                )
            ))
        }

        contentView.addThemeChangingViewForRange(
            "Font size factor:",
            themeManager.selected_theme.typefaces.currentProgress()
        ) { progress ->
            val currentTheme = themeManager.selected_theme
            themeManager.changeTheme(currentTheme.custom(
                newTypefaces = CoreTheme.defaultTypefaces.scaledByProgress(progress)
            ))
        }

        contentView.addThemeChangingViewForRange(
            "Rounded corners factor:",
            themeManager.selected_theme.shapes.currentProgress()
        ) { progress ->
            val currentTheme = themeManager.selected_theme
            themeManager.changeTheme(currentTheme.custom(
                newShapes = CoreTheme.defaultShapes.scaledByProgress(progress)
            ))
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        if (isAnimationEnabled) {
            translationX = w.toFloat()

            val animator = ValueAnimator.ofFloat(w.toFloat(), 0f)
            animator.duration = 333L
            animator.addUpdateListener {
                translationX = it.animatedValue as Float
            }
            animator.doOnEnd {
                hasEndedAnimation.invoke()
            }
            animator.start()
        }
    }

    private fun CoreLinearLayout.addThemeChangingViewsForColors(
        title: String,
        selectedColor: Int,
        colors: List<Pair<String, Int>>,
        changeValueListener: (newColor: Int) -> Unit
    ) {
        val titleView = CoreTextView(context, typeface = TypefaceAttribute.Caption1)
        titleView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginTop(context.dp(16)))
        titleView.text = title
        addView(titleView)

        val selectedValueGroupView = RadioGroup(context)
        selectedValueGroupView.orientation = VERTICAL
        selectedValueGroupView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginTop(context.dp(12)))
        addView(selectedValueGroupView)

        colors.forEach { (title, value) ->
            val valueRadioButtonView = CoreRadioButton(
                ctx = context,
                tintColor = ColorAttribute.HardcodedColor(value)
            )
            valueRadioButtonView.padding(context.dp(16))
            valueRadioButtonView.id = View.generateViewId()
            valueRadioButtonView.isChecked = value == selectedColor
            valueRadioButtonView.text = title
            valueRadioButtonView.setOnCheckedChangeListener { _, checked ->
                if (checked) changeValueListener.invoke(value)
            }
            valueRadioButtonView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginBottom(context.dp(8)))
            selectedValueGroupView.addView(valueRadioButtonView)
        }
    }

    private fun CoreLinearLayout.addThemeChangingViewForRange(
        title: String,
        progress: Int = 0,
        onValueChangeListener: (Int) -> Unit = {}
    ) {
        val titleView = CoreTextView(context, typeface = TypefaceAttribute.Caption1)
        titleView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginTop(context.dp(16)))
        titleView.text = title
        addView(titleView)

        val rangeView = CoreSeekBar(context)
        rangeView.progress = progress
        rangeView.layoutParams(linearLayoutParams().matchWidth().wrapHeight().marginTop(context.dp(12)))
        rangeView.setOnSeekBarChangeListener(object: OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    onValueChangeListener.invoke(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        addView(rangeView)
    }

}