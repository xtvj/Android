package io.github.xtvj.android.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import io.github.xtvj.android.R


class FrequencyView : View {

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)

    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr) {
        val ta = ctx.obtainStyledAttributes(attrs, R.styleable.FrequencyView)
        frequency_progress_color = ta.getColor(R.styleable.FrequencyView_frequency_progress_color, frequency_progress_color)
        frequency_progress = ta.getFloat(R.styleable.FrequencyView_frequency_progress, frequency_progress)
        frequency_start_color = ta.getColor(R.styleable.FrequencyView_frequency_start_color, frequency_start_color)
        frequency_middle_color = ta.getColor(R.styleable.FrequencyView_frequency_middle_color, frequency_middle_color)
        frequency_middle2_color = ta.getColor(R.styleable.FrequencyView_frequency_middle2_color, frequency_middle2_color)
        frequency_end_color = ta.getColor(R.styleable.FrequencyView_frequency_end_color, frequency_end_color)
        frequency_ring_color = ta.getColor(R.styleable.FrequencyView_frequency_ring_color, frequency_ring_color)
        frequency_arrow_color = ta.getColor(R.styleable.FrequencyView_frequency_arrow_color, frequency_arrow_color)
        ta.recycle()
    }

    var frequency_progress = 0.0f
        set(value) {
            field = value
            invalidate()
        }
    var frequency_progress_color = Color.rgb(26, 32, 46)
        set(value) {
            field = value
            invalidate()
        }
    var frequency_start_color = Color.rgb(13, 105, 248)
        set(value) {
            field = value
            invalidate()
        }
    var frequency_middle_color = Color.rgb(3, 165, 101)
        set(value) {
            field = value
            invalidate()
        }
    var frequency_middle2_color = Color.rgb(255, 149, 0)
        set(value) {
            field = value
            invalidate()
        }
    var frequency_end_color = Color.rgb(255, 55, 81)
        set(value) {
            field = value
            invalidate()
        }
    var frequency_ring_color = Color.rgb(113, 123, 143)
        set(value) {
            field = value
            invalidate()
        }
    var frequency_arrow_color = Color.rgb(13, 105, 248)
        set(value) {
            field = value
            invalidate()
        }

    var frequency_first_circle_width = 40f
        set(value) {
            field = value
            invalidate()
        }
    var frequency_ring_width = 5f
        set(value) {
            field = value
            invalidate()
        }
    var frequency_gap = 20f
        set(value) {
            field = value
            invalidate()
        }

    //画笔
    private val circlePaint = Paint()
    private val secondCirclePaint = Paint()
    private val arrowPaint = Paint()

    //绘制矩形
    private val rectF = RectF()
    private val rectF2 = RectF()
    private var path = Path()

    init {
        circlePaint.strokeWidth = frequency_first_circle_width
        circlePaint.strokeJoin = Paint.Join.ROUND
        circlePaint.style = Paint.Style.STROKE
        circlePaint.isAntiAlias = true
        circlePaint.color = frequency_start_color

        secondCirclePaint.strokeWidth = frequency_ring_width
        secondCirclePaint.strokeJoin = Paint.Join.ROUND
        secondCirclePaint.style = Paint.Style.STROKE
        secondCirclePaint.isAntiAlias = true
        secondCirclePaint.color = frequency_ring_color

        arrowPaint.strokeWidth = frequency_ring_width
        arrowPaint.strokeJoin = Paint.Join.ROUND
        arrowPaint.style = Paint.Style.STROKE
        arrowPaint.isAntiAlias = true
        arrowPaint.color = frequency_arrow_color
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initView()
    }

    private fun initView() {

        circlePaint.color = frequency_start_color
        secondCirclePaint.color = frequency_ring_color
        //矩形
        rectF.set(
            0f + frequency_first_circle_width / 2,
            0f + frequency_first_circle_width / 2,
            width.toFloat() - frequency_first_circle_width / 2,
            height.toFloat() - frequency_first_circle_width / 2
        )
        rectF2.set(
            frequency_first_circle_width + frequency_gap + frequency_ring_width / 2,
            frequency_first_circle_width + frequency_gap + frequency_ring_width / 2,
            width.toFloat() - frequency_first_circle_width - frequency_gap - frequency_ring_width / 2,
            height.toFloat() - frequency_first_circle_width
        )
        linearGradient = LinearGradient(
            0f, 0f, (measuredWidth).toFloat(), 0f,
            intArrayOf(frequency_start_color, frequency_middle_color, frequency_middle2_color, frequency_end_color),
            floatArrayOf(0f, 0.333f, 0.666f, 1.0f),
            Shader.TileMode.MIRROR
        )
        path.moveTo(rectF2.left + frequency_ring_width / 2, height.toFloat() / 2)
        path.lineTo(frequency_first_circle_width + rectF2.left + frequency_ring_width / 2, height.toFloat() / 2)
        path.moveTo(rectF2.left + frequency_ring_width / 2, height.toFloat() / 2)
        path.lineTo(frequency_first_circle_width / 3 + rectF2.left + frequency_ring_width / 2, height.toFloat() / 2 - frequency_first_circle_width / 3)
        path.moveTo(rectF2.left + frequency_ring_width / 2, height.toFloat() / 2)
        path.lineTo(frequency_first_circle_width / 3 + rectF2.left + frequency_ring_width / 2, height.toFloat() / 2 + frequency_first_circle_width / 3)
    }

    private var linearGradient = LinearGradient(
        0f, 0f, (measuredWidth).toFloat(), 0f,
        intArrayOf(frequency_start_color, frequency_middle_color, frequency_middle2_color, frequency_end_color),
        floatArrayOf(0f, 0.333f, 0.666f, 1.0f),
        Shader.TileMode.MIRROR
    )

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        circlePaint.shader = linearGradient
        //画最外层圆形分隔线
        repeat(19) {
            canvas?.drawArc(
                rectF, 180f + (it * 9.94).toFloat(),
                1f, false, circlePaint
            )
        }
        //半圆弧
        canvas?.drawArc(
            rectF2, 180f,
            180f, false, secondCirclePaint
        )
        canvas?.save()
        //以中心点旋转画布
        canvas?.rotate(frequency_progress * 180, width.toFloat() / 2, height.toFloat() / 2)
        //箭头，表示进度
        canvas?.drawPath(path, arrowPaint)
        canvas?.restore()
    }

}