package io.github.xtvj.android.customView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color.rgb
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import io.github.xtvj.android.R
import kotlin.math.min

class CheckCircleView : View {

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)

    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr) {
        val ta = ctx.obtainStyledAttributes(attrs, R.styleable.CheckCircleView)
        checkBackgroundColor = ta.getColor(R.styleable.CheckCircleView_check_background_color, checkBackgroundColor)
        checkForegroundColor = ta.getColor(R.styleable.CheckCircleView_check_foreground_color, checkForegroundColor)
        circleFirstWidth = ta.getDimensionPixelSize(R.styleable.CheckCircleView_check_circle_first_width, circleFirstWidth)
        circleSecondWidth = ta.getDimensionPixelSize(R.styleable.CheckCircleView_check_circle_second_width, circleSecondWidth)
        circleFourthWidth = ta.getDimensionPixelSize(R.styleable.CheckCircleView_check_circle_fourth_width, circleFourthWidth)
        radiusSecond = ta.getFloat(R.styleable.CheckCircleView_check_radius_second, radiusSecond)
        radiusThird = ta.getFloat(R.styleable.CheckCircleView_check_radius_third, radiusThird)
        radiusFourth = ta.getFloat(R.styleable.CheckCircleView_check_radius_fourth, radiusFourth)
        radianStart = ta.getFloat(R.styleable.CheckCircleView_check_radian_start, radianStart)
        radianEnd = ta.getFloat(R.styleable.CheckCircleView_check_radian_end, radianEnd)
        strokeCap = when (ta.getInteger(R.styleable.CheckCircleView_check_stroke_cap, 0)) {
            0 -> Paint.Cap.BUTT
            1 -> Paint.Cap.ROUND
            else -> Paint.Cap.SQUARE
        }
        ta.recycle()
    }

    //背景色
    var checkBackgroundColor = rgb(1, 150, 255)
        set(value) {
            field = value
            invalidate()
        }

    //按压时颜色
    var checkForegroundColor = rgb(122, 195, 249)
        set(value) {
            field = value
            invalidate()
        }

    //最外圆宽度
    var circleFirstWidth = 35
        set(value) {
            field = value
            invalidate()
        }
    var circleSecondWidth = 3
        set(value) {
            field = value
            invalidate()
        }
    var circleFourthWidth = 10
        set(value) {
            field = value
            invalidate()
        }

    //圆环半径比例
    var radiusSecond = 0.85f
        set(value) {
            field = value
            invalidate()
        }
    var radiusThird = 0.75f
        set(value) {
            field = value
            invalidate()
        }
    var radiusFourth = 0.7f
        set(value) {
            field = value
            invalidate()
        }

    var radianStart = 60f
        set(value) {
            field = value
            invalidate()
        }
    var radianEnd = 300f
        set(value) {
            field = value
            invalidate()
        }

    //线帽样式
    var strokeCap = Paint.Cap.BUTT
        set(value) {
            field = value
            bgPaint.strokeCap = value
            invalidate()
        }

    //画笔
    private val bgPaint = Paint()
    private val insidePaint = Paint()

    //绘制矩形
    private val rectF = RectF()
    private val insideRrectF = RectF()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initSize()
    }

    private fun initSize() {
        bgPaint.strokeCap = strokeCap
        bgPaint.strokeJoin = Paint.Join.ROUND
        bgPaint.style = Paint.Style.STROKE
        bgPaint.isAntiAlias = true
        bgPaint.color = checkBackgroundColor
        insidePaint.strokeJoin = Paint.Join.ROUND
        insidePaint.style = Paint.Style.FILL
        insidePaint.color = checkBackgroundColor
        insidePaint.isAntiAlias = true
        val length = min(width, height)
        rectF.set(
            (width - length) / 2.toFloat() + circleFirstWidth,
            (height - length) / 2.toFloat() + circleFirstWidth,
            (width - length) / 2.toFloat() + length - circleFirstWidth,
            (height - length) / 2.toFloat() + length - circleFirstWidth
        )
        insideRrectF.set(
            (width - length) / 2.toFloat() + (1 - radiusFourth) * length / 2 + circleFourthWidth,
            (height - length) / 2.toFloat() + (1 - radiusFourth) * length / 2 + circleFourthWidth,
            (width - length) / 2.toFloat() + (1 - radiusFourth) * length / 2 - circleFourthWidth + radiusFourth * length,
            (height - length) / 2.toFloat() + (1 - radiusFourth) * length / 2 - circleFourthWidth + radiusFourth * length
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        bgPaint.color = if (isPressed) checkForegroundColor else checkBackgroundColor
        bgPaint.textSize = 20f
        val times = (radianEnd - radianStart) / 3
        bgPaint.strokeWidth = circleFirstWidth.toFloat()
        bgPaint.color = rgb(61, 175, 252)
        //最外层间隔圆环
        repeat(times.toInt()) {
            canvas?.drawArc(
                rectF, radianStart + it * 3,
                2f, false, bgPaint
            )
        }
        //最外层连续圆环
        canvas?.drawArc(
            rectF, radianEnd + 10,
            340 - (radianEnd - radianStart), false, bgPaint
        )
        bgPaint.strokeWidth = circleSecondWidth.toFloat()
        //第二个圆
        canvas?.drawCircle(width.toFloat() / 2, height.toFloat() / 2, radiusSecond * min(width, height) / 2, bgPaint)

        //里面填充的圆
        canvas?.drawCircle(width.toFloat() / 2, height.toFloat() / 2, radiusThird * min(width, height) / 2, insidePaint)
        bgPaint.color = rgb(255, 255, 255)
        bgPaint.strokeWidth = circleFourthWidth.toFloat()
        //最里面三个圆环
        repeat(3) {
            canvas?.drawArc(
                insideRrectF, (20 + (it) * 120).toFloat(),
                100f, false, bgPaint
            )
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                insidePaint.color = checkForegroundColor
                invalidate()
            }
            MotionEvent.ACTION_CANCEL -> {
                insidePaint.color = checkBackgroundColor
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                insidePaint.color = checkBackgroundColor
                invalidate()
                performClick()
            }
        }
        return true
    }

}
