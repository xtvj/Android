package io.github.xtvj.android.customView

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.*
import android.view.animation.*
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.*
import androidx.core.content.ContextCompat
import io.github.xtvj.android.R
import io.github.xtvj.common.utils.ScreenUtils.dpToPx

class SlidingButton : FrameLayout {

    private val inflatedView: View
    private lateinit var slidingImage: ImageView
    private lateinit var slidingIndicator: View

    var slidingListener: OnSlidingListener? = null

    private var middleOfButton = 0f
    private var endOfButton = 0f

    var buttonBackground: Drawable? = null
        set(value) {
            field = value
            if (::slidingImage.isInitialized)
                slidingImage.background = value
        }
    var buttonIcon: Drawable? = null
        set(value) {
            field = value
            if (::slidingImage.isInitialized)
                slidingImage.setImageDrawable(value)
        }
    var iconScaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_INSIDE
        set(value) {
            when (value) {
                ImageView.ScaleType.CENTER_INSIDE,
                ImageView.ScaleType.CENTER_CROP,
                ImageView.ScaleType.CENTER,
                ImageView.ScaleType.FIT_CENTER,
                ImageView.ScaleType.FIT_XY -> {
                    field = value
                    if (::slidingImage.isInitialized) slidingImage.scaleType = value
                }

                else -> throw IllegalArgumentException("ScaleType $value aren't allowed, please use CENTER, CENTER_CROP, CENTER_INSIDE,FIT_CENTER, or FIT_XY")
            }
        }
    var buttonWidth: Int = 0
        set(value) {
            field = value
            if (::slidingImage.isInitialized) slidingImage.layoutParams.width = value
        }
    var buttonHeight: Int = 0
        set(value) {
            field = value
            if (::slidingImage.isInitialized) slidingImage.layoutParams.height = value
        }

    var trackBackground: Drawable? = null
        set(value) {
            field = value
            if (::slidingIndicator.isInitialized) {
                slidingIndicator.background = value
            }
        }
    var trackBackgroundTint: ColorStateList? = null
        set(value) {
            field = value
            if (::slidingIndicator.isInitialized) {
                if (value != null) {
                    slidingIndicator.background?.colorFilter = PorterDuffColorFilter(
                        value.getColorForState(slidingIndicator.drawableState, value.defaultColor),
                        PorterDuff.Mode.SRC_IN
                    )
                }
            }
        }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        0
    )

    constructor(
        _context: Context,
        attrs: AttributeSet?,
        defStyleInt: Int
    ) : super(
        _context,
        attrs,
        defStyleInt
    ) {

        val arr = context.obtainStyledAttributes(
            attrs,
            R.styleable.SlidingButton
        )

        val defaultButtonDrawable =
            ContextCompat.getDrawable(context, R.drawable.ic_default_slide_icon_new)
        buttonIcon = arr.getDrawable(R.styleable.SlidingButton_sliding_button_icon)
            ?: defaultButtonDrawable

        val stateListIconTint = arr.getColorStateList(
            R.styleable.SlidingButton_sliding_button_icon_tint
        ) ?: ColorStateList.valueOf(Color.GRAY)

        buttonBackground = arr.getDrawable(R.styleable.SlidingButton_sliding_button_background)

        val defaultButtonSize = (46).dpToPx.toInt()
        buttonWidth = arr.getDimensionPixelSize(
            R.styleable.SlidingButton_sliding_button_width,
            defaultButtonSize
        )
        buttonHeight = arr.getDimensionPixelSize(
            R.styleable.SlidingButton_sliding_button_height,
            defaultButtonSize
        )

        val scaleName = arr.getInteger(R.styleable.SlidingButton_sliding_icon_scaleType, 7).let {
            when (it) {
                1 -> "FIT_XY"
                3 -> "FIT_CENTER"
                5 -> "CENTER"
                6 -> "CENTER_CROP"
                else -> "CENTER_INSIDE"
            }
        }
        iconScaleType = ImageView.ScaleType.valueOf(scaleName)

        trackBackground = arr.getDrawable(R.styleable.SlidingButton_sliding_trackBackground)

        trackBackgroundTint = arr.getColorStateList(
            R.styleable.SlidingButton_sliding_trackBackgroundTint
        )

        buttonIcon?.colorFilter = PorterDuffColorFilter(
            stateListIconTint.defaultColor,
            PorterDuff.Mode.SRC_IN
        )
        if (trackBackgroundTint != null && trackBackground != null) {
            trackBackground?.colorFilter = PorterDuffColorFilter(
                trackBackgroundTint!!.getColorForState(
                    trackBackground!!.state,
                    trackBackgroundTint!!.defaultColor
                ),
                PorterDuff.Mode.SRC_IN
            )
        }

        arr.recycle()

        inflatedView = LayoutInflater.from(context).inflate(R.layout.layout_button, this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        slidingIndicator = inflatedView.findViewById(R.id.slidingIndicator)
        slidingImage = inflatedView.findViewById(R.id.slidingImage)

        configureTrackView()
        configureImageView()

        configureTouch()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                parent.requestDisallowInterceptTouchEvent(true)
            }

            MotionEvent.ACTION_CANCEL -> {
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun configureTrackView() {
        slidingIndicator.background = trackBackground
    }

    private fun configureImageView() {
        slidingImage.background = buttonBackground
        slidingImage.layoutParams.let { it as LayoutParams }.also {
            it.width = buttonWidth
            it.height = buttonHeight
            slidingImage.layoutParams = it
        }

        slidingImage.scaleType = iconScaleType
        slidingImage.setImageDrawable(buttonIcon)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        middleOfButton = h.toFloat() / 2 - buttonHeight.toFloat() / 2
        endOfButton = h.toFloat() - paddingBottom.toFloat()
    }

    override fun removeAllViews() = throw IllegalStateException("This method isn't allowed ")

    override fun removeView(view: View?) = throw IllegalStateException("This method isn't allowed ")

    override fun removeViewAt(index: Int) =
        throw IllegalStateException("This method isn't allowed ")

    @SuppressLint("ClickableViewAccessibility")
    private fun configureTouch() {
        this.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> true
                MotionEvent.ACTION_MOVE -> onMove(
                    event,
                    (paddingTop).toFloat(),
                    endOfButton
                )

                MotionEvent.ACTION_UP -> {
                    animatedToStart()
                    true
                }

                else -> true
            }
        }
    }

    private fun animatedToStart() {
        val floatAnimator = ValueAnimator.ofFloat(slidingImage.y, middleOfButton)
        floatAnimator.addUpdateListener {
            updateSlidingXPosition(it.animatedValue as Float)
        }
        floatAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationStart(animation: Animator) {}
        })
        floatAnimator.duration = 115L
        floatAnimator.interpolator = AccelerateDecelerateInterpolator()
        floatAnimator.start()
    }

    private fun onMove(event: MotionEvent, start: Float, end: Float): Boolean {
        if (event.y in start..end) {
            updateSlidingXPosition(event.y)
            return true
        }
        return false
    }

    private fun updateSlidingXPosition(y: Float) {
        slidingImage.y = y
        val percent = y / (endOfButton - paddingTop - paddingBottom)
        slidingListener?.onSliding(percent)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        slidingImage.isEnabled = enabled
        slidingIndicator.isEnabled = enabled
    }

    fun setButtonIcon(@DrawableRes resId: Int) {
        buttonIcon = ContextCompat.getDrawable(context, resId)
    }

    fun setButtonBackground(@DrawableRes resId: Int) {
        buttonBackground = ContextCompat.getDrawable(context, resId)
    }

    fun setButtonBackgroundColor(@ColorInt color: Int) {
        when (buttonBackground) {
            is ColorDrawable -> (slidingImage.background as ColorDrawable).color = color
            else -> buttonBackground = ColorDrawable(color)
        }
    }

    interface OnSlidingListener {
        fun onSliding(progress: Float)
    }

}