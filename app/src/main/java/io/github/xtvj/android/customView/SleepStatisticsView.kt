package io.github.xtvj.android.customView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.toRect
import io.github.xtvj.android.R


@SuppressLint("UseCompatLoadingForDrawables")
class SleepStatisticsView : View {

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)

    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr)

    private val outsetCircleRectF = RectF()
    private val insetCircleRectF = RectF()
    private val moonOrSunRectF = RectF()

    private val outsetPaint = Paint()
    private val insetPaint = Paint()

    private val sun = resources.getDrawable(R.drawable.icon_tsun2, null)
    private val moon = resources.getDrawable(R.drawable.icon_tmoom2, null)

    var startAngle = 0f
        set(value) {
            field = value
            invalidate()
        }
    var endAngle = 184f
        set(value) {
            field = value
            invalidate()
        }

    var outsetCircleWith = 60f
        set(value) {
            field = value
            invalidate()
        }
    var insetCircleWidth = 25f
        set(value) {
            field = value
            invalidate()
        }
    var outsetCircleBackgroundColor = Color.rgb(26, 32, 46)
        set(value) {
            field = value
            invalidate()
        }
    var outsetCircleColor = Color.rgb(13, 105, 248)
        set(value) {
            field = value
            invalidate()
        }
    var insetCircleColor = Color.rgb(89, 89, 89)
        set(value) {
            field = value
            invalidate()
        }

    init {
        outsetPaint.strokeWidth = outsetCircleWith
        outsetPaint.color = outsetCircleBackgroundColor
        outsetPaint.strokeJoin = Paint.Join.ROUND
        outsetPaint.strokeCap = Paint.Cap.ROUND
        outsetPaint.style = Paint.Style.STROKE
        outsetPaint.isAntiAlias = true

        insetPaint.strokeWidth = insetCircleWidth
        insetPaint.color = insetCircleColor
        insetPaint.strokeJoin = Paint.Join.ROUND
        insetPaint.style = Paint.Style.STROKE
        insetPaint.isAntiAlias = true

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        outsetPaint.color = outsetCircleBackgroundColor
        canvas?.drawArc(
            outsetCircleRectF, 0f,
            360f, false, outsetPaint
        )
        canvas?.save()
        outsetPaint.color = outsetCircleColor
        canvas?.drawArc(
            outsetCircleRectF, startAngle,
            endAngle, false, outsetPaint
        )

        canvas?.save()
        canvas?.rotate(startAngle, width.toFloat() / 2, height.toFloat() / 2)
        moon.bounds = moonOrSunRectF.apply {
            set(
                0f,
                height.toFloat() / 2 -  outsetCircleWith,
                outsetCircleWith,
                height.toFloat() / 2
            )
        }.toRect()
        canvas?.let {
            moon.draw(canvas)
        }
        canvas?.restore()
        canvas?.rotate(endAngle, width.toFloat() / 2, height.toFloat() / 2)
        sun.bounds = moonOrSunRectF.apply {
            set(
                0f,
                height.toFloat() / 2,
                outsetCircleWith,
                height / 2 + outsetCircleWith
            )
        }.toRect()
        canvas?.let {
            sun.draw(canvas)
        }

        //画内圆
        repeat(48) {
            if (it % 4 == 0) {
                insetPaint.color = Color.WHITE
                insetPaint.strokeWidth = insetCircleWidth * 3 / 2
            } else {
                insetPaint.strokeWidth = insetCircleWidth
                insetPaint.color = insetCircleColor
            }
            canvas?.drawArc(
                insetCircleRectF, (it * 7.5).toFloat(),
                1f, false, insetPaint
            )
            canvas?.save()
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initView()
    }

    private fun initView() {
        outsetCircleRectF.set(
            outsetCircleWith / 2,
            outsetCircleWith / 2,
            width - outsetCircleWith / 2,
            height - outsetCircleWith / 2,
        )
        insetCircleRectF.set(
            outsetCircleWith + insetCircleWidth * 3 / 2,
            outsetCircleWith + insetCircleWidth * 3 / 2,
            width - outsetCircleWith - insetCircleWidth * 3 / 2,
            height - outsetCircleWith - insetCircleWidth * 3 / 2
        )
        moonOrSunRectF.set(
            0f,
            height.toFloat() / 2,
            outsetCircleWith,
            height / 2 + outsetCircleWith
        )
        outsetPaint.strokeWidth = outsetCircleWith
        outsetPaint.color = outsetCircleBackgroundColor
    }
}