package io.github.xtvj.android.customView

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.toRect
import io.github.xtvj.android.R
import kotlin.math.abs


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

    private var startAngle = 0f
    private var endAngle = 0f

    fun setSweepAngle(start: Float, end: Float) {
        startAngle = start
        endAngle = end
        invalidate()
    }

    var outsetCircleWith = 0.053333335f * Resources.getSystem().displayMetrics.widthPixels
        set(value) {
            field = value
            invalidate()
        }
    var insetCircleWidth = 0.013333334f * Resources.getSystem().displayMetrics.widthPixels
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

    /**
     * 月亮与太阳图标距离外圆环边缘与外圆环宽度的比例
     */
    private val moonOrSunPercent: Float = 1f / 7f

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
        //只有开始有结果值不相同时才绘制
        if (abs(endAngle - startAngle) > 0) {
            //画进度圆
            outsetPaint.color = outsetCircleColor
            canvas?.drawArc(
                outsetCircleRectF, startAngle,
                endAngle - startAngle, false, outsetPaint
            )
            canvas?.save()
            //画月亮
            canvas?.rotate(startAngle, width.toFloat() / 2, height.toFloat() / 2)
            moon.bounds = moonOrSunRectF.toRect()
            canvas?.let {
                moon.draw(canvas)
            }
            canvas?.restore()
            //画太阳
            canvas?.rotate(endAngle, width.toFloat() / 2, height.toFloat() / 2)
            sun.bounds = moonOrSunRectF.toRect()
            canvas?.let {
                sun.draw(canvas)
            }
            canvas?.restore()
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
        outsetPaint.strokeWidth = outsetCircleWith
        outsetPaint.color = outsetCircleBackgroundColor
        moonOrSunRectF.apply {
            set(
                width - outsetCircleWith + outsetCircleWith * moonOrSunPercent,
                height.toFloat() / 2 - outsetCircleWith / 2 + outsetCircleWith * moonOrSunPercent,
                width - outsetCircleWith * moonOrSunPercent,
                height.toFloat() / 2 + outsetCircleWith / 2 - outsetCircleWith * moonOrSunPercent
            )
        }
    }
}