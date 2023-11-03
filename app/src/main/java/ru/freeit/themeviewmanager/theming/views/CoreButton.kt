package ru.freeit.themeviewmanager.theming.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.view.Gravity
import android.view.MotionEvent
import android.view.PointerIcon
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import ru.freeit.themeviewmanager.theming.CoreTheme
import ru.freeit.themeviewmanager.theming.colors.ColorAttribute
import ru.freeit.themeviewmanager.theming.extensions.dp
import ru.freeit.themeviewmanager.theming.extensions.padding
import ru.freeit.themeviewmanager.theming.shape.ShapeAttribute
import ru.freeit.themeviewmanager.theming.typeface.TypefaceAttribute

class CoreButton @JvmOverloads constructor(
    ctx: Context,
    private val shape: ShapeAttribute = ShapeAttribute.Small,
    private val backgroundColor: ColorAttribute = ColorAttribute.PrimaryColor,
    private val disabledBackgroundColor: ColorAttribute = ColorAttribute.DisabledBackgroundColor,
    disabledTextColor: ColorAttribute = ColorAttribute.DisabledTextColor,
    private val rippleColor: ColorAttribute = ColorAttribute.PrimaryDarkColor,
    textColor: ColorAttribute = ColorAttribute.ColorOnPrimary,
    typeface: TypefaceAttribute = TypefaceAttribute.Caption1
) : CoreTextView(ctx, textColor = textColor, disabledTextColor = disabledTextColor, typeface = typeface) {

    init {
        isClickable = true
        isFocusable = true
        gravity = Gravity.CENTER
        padding(horizontal = context.dp(8), vertical = context.dp(12))
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        themeManager.listenForThemeChanges(::onThemeChanged)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        themeManager.doNotListenForThemeChanges(::onThemeChanged)
    }

    override fun onThemeChanged(theme: CoreTheme) {
        super.onThemeChanged(theme)

        val contentDrawable = theme.shapes[shape].drawable(context)
        contentDrawable.setTint(theme.colors[backgroundColor])

        val disabledContentDrawable = theme.shapes[shape].drawable(context)
        disabledContentDrawable.setTint(theme.colors[disabledBackgroundColor])

        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(intArrayOf(android.R.attr.state_enabled), contentDrawable)
        stateListDrawable.addState(intArrayOf(-android.R.attr.state_enabled), disabledContentDrawable)

        val color = ColorStateList.valueOf(theme.colors[rippleColor])

        background = RippleDrawable(color, stateListDrawable, null)
    }

    override fun onInitializeAccessibilityEvent(event: AccessibilityEvent?) {
        super.onInitializeAccessibilityEvent(event)
        event?.className = Button::class.java.name
    }

    override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(info)
        info?.className = Button::class.java.name
    }

    override fun getAccessibilityClassName(): CharSequence {
        return Button::class.java.name
    }

    override fun onResolvePointerIcon(event: MotionEvent?, pointerIndex: Int): PointerIcon {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (pointerIcon == null && isClickable && isEnabled) {
                return PointerIcon.getSystemIcon(context, PointerIcon.TYPE_HAND)
            }
        }
        return super.onResolvePointerIcon(event, pointerIndex)
    }

}