package io.github.xtvj.android.customView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
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
        frequency_text_start = ta.getString(R.styleable.FrequencyView_frequency_text_start) ?: frequency_text_start
        frequency_text_end = ta.getString(R.styleable.FrequencyView_frequency_text_end) ?: frequency_text_end
        frequency_text_start_color = ta.getColor(R.styleable.FrequencyView_frequency_text_start_color, frequency_text_start_color)
        frequency_text_start_size = ta.getDimensionPixelSize(R.styleable.FrequencyView_frequency_text_start_size, frequency_text_start_size)
        frequency_progress_color = ta.getColor(R.styleable.FrequencyView_frequency_progress_color, frequency_progress_color)
        frequency_progress_size = ta.getDimensionPixelSize(R.styleable.FrequencyView_frequency_progress_size, frequency_progress_size)
        frequency_progress = ta.getFloat(R.styleable.FrequencyView_frequency_progress, frequency_progress)
        frequency_start_color = ta.getColor(R.styleable.FrequencyView_frequency_start_color, frequency_start_color)
        frequency_end_color = ta.getColor(R.styleable.FrequencyView_frequency_end_color, frequency_end_color)
        frequency_ring_color = ta.getColor(R.styleable.FrequencyView_frequency_ring_color, frequency_ring_color)
        frequency_arrow_color = ta.getColor(R.styleable.FrequencyView_frequency_arrow_color, frequency_arrow_color)
        ta.recycle()
    }

    var frequency_text_start = "60"
    var frequency_text_end = "120"
    var frequency_text_start_color = Color.rgb(26, 32, 46)
    var frequency_text_start_size = 13
    var frequency_progress = 0.5f
    var frequency_progress_color = Color.rgb(26, 32, 46)
    var frequency_progress_size = 19
    var frequency_start_color = Color.rgb(13, 105, 248)
    var frequency_end_color = Color.rgb(255, 55, 81)
    var frequency_ring_color = Color.rgb(113, 123, 143)
    var frequency_arrow_color = Color.rgb(26, 32, 46)

    var frequency_first_circle_width = 80f
    var frequency_ring_width = 10f
    var frequency_gap =20f

    //画笔
    private val circlePaint = Paint()
    private val secondCirclePaint = Paint()
    private val textPaint = Paint()

    //绘制矩形
    private val rectF = RectF()
    private val rectF2 = RectF()

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
            0f, 0f, (measuredWidth).toFloat(),
            0f,
            frequency_start_color,
            frequency_end_color,
            Shader.TileMode.MIRROR
        )
    }

    private var linearGradient = LinearGradient(
        0f, 0f, (measuredWidth).toFloat(),
        0f,
        frequency_start_color,
        frequency_end_color,
        Shader.TileMode.MIRROR
    )

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //画背景环形
        circlePaint.shader = linearGradient
        repeat(19) {
            canvas?.drawArc(
                rectF, 180f + (it * 9.94).toFloat(),
                1f, false, circlePaint
            )
        }
        canvas?.drawArc(
            rectF2, 180f,
            180f, false, secondCirclePaint
        )

    }

}