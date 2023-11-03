# ThemeViewManager
A simple example of an Android app with a custom implementation of View themes.

### Screen record

[screen_record](https://github.com/evitwilly/ThemeViewManager/assets/40917658/2b9a65ee-76d0-4064-a7f2-c7c591870bc8)

### Description

This example doesn't contain xml layouts and Jetpack Compose code, only descendants of different View (Button, TextView, e.t.c).

Let's check the Button code for example:

    class CoreButton @JvmOverloads constructor(
        ctx: Context,
        private val shape: ShapeAttribute = ShapeAttribute.Small,
        private val backgroundColor: ColorAttribute = ColorAttribute.PrimaryColor,
        private val disabledBackgroundColor: ColorAttribute = ColorAttribute.DisabledBackgroundColor,
        private val rippleColor: ColorAttribute = ColorAttribute.PrimaryDarkColor,
        disabledTextColor: ColorAttribute = ColorAttribute.DisabledTextColor,
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

As you can see in the <code>onAttachedToWindow</code> method we subscribe to the theme changes and receive parameters such as color by attributes. A similar principle is implemented in the [Telegram app](https://github.com/DrKLO/Telegram)

Also a little logic was added for [Accessibility](https://developer.android.com/guide/topics/ui/accessibility) since the CoreButton doesn't inherit from the AppCompatButton.









