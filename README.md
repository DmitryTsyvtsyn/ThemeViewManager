# ThemeViewManager
A simple example of an Android app with a custom implementation of View themes.

### Screen record

[screen_record](https://github.com/evitwilly/ThemeViewManager/assets/40917658/2b9a65ee-76d0-4064-a7f2-c7c591870bc8)

### Description

This example doesn't contain xml layouts and Jetpack Compose library, only descendants of different View classes (ImageView, TextView, e.t.c).

Let's check the CoreButton code for example:

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

As you can see the <code>onAttachedToWindow</code> method subscribes to the theme changes and receive parameters such as color by attributes. A similar principle is implemented in the [Telegram app](https://github.com/DrKLO/Telegram)

Also a little logic was added for [Accessibility](https://developer.android.com/guide/topics/ui/accessibility) since the CoreButton doesn't inherit from the AppCompatButton.

<code>themeManager</code> is a base class that contains subscriptions and notifies Views when the theme changes:

    class CoreThemeManager(private val assetManager: AssetManager) {
    
        private val listeners = mutableListOf<(CoreTheme) -> Unit>()
    
        private var currentTheme = CoreTheme.LIGHT
    
        val selected_theme: CoreTheme
            get() = currentTheme
    
        fun listenForThemeChanges(listener: (CoreTheme) -> Unit) {
            listeners.add(listener)
            listener.invoke(currentTheme)
        }
    
        fun doNotListenForThemeChanges(listener: (CoreTheme) -> Unit) {
            listeners.remove(listener)
        }
    
        fun toggleTheme() {
            currentTheme = if (currentTheme == CoreTheme.LIGHT) CoreTheme.DARK else CoreTheme.LIGHT
            listeners.forEach { listener -> listener.invoke(currentTheme) }
        }
    
        ...
    
    }

The implementation of the theme is quite simple:

    enum class CoreTheme(
        val colors: Colors,
        val typefaces: Typefaces = Typefaces(
            title1 = "sf_pro_rounded_bold.ttf" to 23f,
            caption1 = "sf_pro_rounded_medium.ttf" to 17f,
            body1 = "sf_pro_rounded_regular.ttf" to 17f
        ),
        val shapes: Shapes = Shapes(
            small = ShapeDrawableStrategy.Rounded(8f, 8f, 8f, 8f)
        )
    ) {
    
        LIGHT(
            colors = Colors(
                primaryColor = CoreColors.greenMedium,
                primaryDarkColor = CoreColors.greenDark,
                primaryBackgroundColor = CoreColors.white,
                primaryTextColor = CoreColors.black,
                colorOnPrimary = CoreColors.white,
                disabledTextColor = CoreColors.grayMedium,
                disabledBackgroundColor = CoreColors.grayLight
            )
        ),
    
        DARK(
            colors = Colors(
                primaryColor = CoreColors.greenMedium,
                primaryDarkColor = CoreColors.greenDark,
                primaryBackgroundColor = CoreColors.black,
                primaryTextColor = CoreColors.white,
                colorOnPrimary = CoreColors.white,
                disabledTextColor = CoreColors.grayLight,
                disabledBackgroundColor = CoreColors.grayMedium
            )
        )
    
    }

As you have already seen obtaining color in a View occurs as follows:

    val backgroundColor = theme.colors[backgroundColor]
    val disabledBackgroundColor = theme.colors[disabledBackgroundColor]

This trick is quite simple to implement:

    class Colors(
        private val primaryColor: Int,
        private val primaryDarkColor: Int,
        private val primaryBackgroundColor: Int,
        private val primaryTextColor: Int,
        private val colorOnPrimary: Int,
        private val disabledTextColor: Int,
        private val disabledBackgroundColor: Int
    ) {
    
        operator fun get(attribute: ColorAttribute): Int {
            return when(attribute) {
                is ColorAttribute.PrimaryColor -> primaryColor
                is ColorAttribute.PrimaryDarkColor -> primaryDarkColor
                is ColorAttribute.PrimaryBackgroundColor -> primaryBackgroundColor
                is ColorAttribute.PrimaryTextColor -> primaryTextColor
                is ColorAttribute.ColorOnPrimary -> colorOnPrimary
                is ColorAttribute.DisabledTextColor -> disabledTextColor
                is ColorAttribute.DisabledBackgroundColor -> disabledBackgroundColor
                is ColorAttribute.Transparent -> CoreColors.transparent
                is ColorAttribute.HardcodedColor -> attribute.color
            }
        }
    
    }

You can add as many attributes as you want.

I do not describe all my code since it is already clear and simple. 

Study and share your knowledge with others! 

If you have any questions write: [Telegram](https://t.me/rwcwuwr), [Gmail](mailto:dmitry.kind.2@gmail.com)



