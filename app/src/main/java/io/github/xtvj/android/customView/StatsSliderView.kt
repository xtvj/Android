package io.github.xtvj.android.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import io.github.xtvj.common.utils.ScreenUtils.dpToPx
import io.github.xtvj.common.utils.ScreenUtils.spToPx

class StatsSliderView : View {

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)

    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr)

    //总长度开始值
    var startValue: Int = 0

    //总长度结束值
    var endValue: Int = 160
        set(value) {
            if (value == startValue) {
                throw IllegalStateException("结束值不能与开始值相同")
            } else {
                field = value
            }
            invalidate()
        }

    //正常状态开始值
    var normalStartValue: Int = 60
    var normalEndValue: Int = 100

    //正常状态线条颜色
    var normalColor = Color.rgb(13, 105, 248)

    //最低状态线条颜色
    val minValueColor = Color.rgb(255, 149, 0)

    //最高状态线条颜色
    val maxValueColor = Color.rgb(255, 55, 81)

    //最低值
    private var minValue: Int = 50

    //最高值
    private var maxValue: Int = 120

    fun setValues(min: Int, max: Int) {
        minValue = min
        maxValue = max
        invalidate()
    }

    //状态条高度
    var sliderHeight = (10f).dpToPx

    private val paint = Paint()

    //第一个圆X轴坐标
    private var firstCircle = 0f

    //第二个圆X轴坐标
    private var secondCircle = 0f

    //Y轴中心
    private var yCenter = 0f

    //正常标注文字大小
    var normalTextSize = (14f).spToPx

    //最低最高值标注文字大小
    var valueTextSize = (19f).spToPx

    init {
        paint.strokeWidth = sliderHeight
        paint.color = normalColor
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.SQUARE
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        paint.isFakeBoldText = false
        paint.textAlign = Paint.Align.CENTER
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        firstCircle = width.toFloat() * (minValue - startValue) / (endValue - startValue)
        secondCircle = width.toFloat() * (maxValue - startValue) / (endValue - startValue)
        yCenter = height.toFloat() / 2
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, widthSize / 5)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = minValueColor
        //画左边线条
        canvas.drawRoundRect(
            0f,
            yCenter - sliderHeight / 2,
            width.toFloat() * (normalStartValue - startValue) / (endValue - startValue),
            yCenter + sliderHeight / 2,
            sliderHeight / 2,
            sliderHeight / 2,
            paint
        )
        canvas.save()
        paint.color = maxValueColor
        //画右边线条
        canvas.drawRoundRect(
            width.toFloat() * (normalEndValue - startValue) / (endValue - startValue),
            yCenter - sliderHeight / 2,
            width.toFloat(),
            height / 2 + sliderHeight / 2,
            sliderHeight / 2,
            sliderHeight / 2,
            paint
        )
        canvas.save()
        paint.color = normalColor
        //相除时注意别被取int值了，不然一直是0或1，取float值正常
        //左边向左移动sliderHeight/2 右边向右移动sliderHeight/2 遮挡上面两个线条的圆弧
        canvas.drawRect(
            width.toFloat() * (normalStartValue - startValue) / (endValue - startValue) - sliderHeight / 2,
            yCenter - sliderHeight / 2,
            width.toFloat() * (normalEndValue - startValue) / (endValue - startValue) + sliderHeight / 2,
            yCenter + sliderHeight / 2, paint
        )
        canvas.save()
        paint.color = Color.WHITE
        //左右两个白色圆
        canvas.drawCircle(firstCircle, yCenter, sliderHeight * 3 / 4, paint)
        canvas.drawCircle(secondCircle, yCenter, sliderHeight * 3 / 4, paint)
        canvas.save()
        //左边小圆
        paint.color = minValueColor
        canvas.drawCircle(firstCircle, yCenter, sliderHeight / 3, paint)
        canvas.save()
        //右边小圆
        paint.color = maxValueColor
        canvas.drawCircle(secondCircle, yCenter, sliderHeight / 3, paint)
        canvas.save()
        paint.textSize = normalTextSize
        paint.color = Color.argb(127, 255, 255, 255)
        //左边正常值
        canvas.drawText(
            normalStartValue.toString(),
            width.toFloat() * (normalStartValue - startValue) / (endValue - startValue) - sliderHeight / 2,
            //文字高度的一半 + Y轴中间点 + 半个线条高度 + 一个线条高度（文字与线条的间隔）
            (paint.fontMetrics.bottom - paint.fontMetrics.top) / 2 + yCenter + sliderHeight * 3 / 2,
            paint
        )
        //右边正常值
        canvas.drawText(
            normalEndValue.toString(),
            width.toFloat() * (normalEndValue - startValue) / (endValue - startValue) + sliderHeight / 2,
            (paint.fontMetrics.bottom - paint.fontMetrics.top) / 2 + yCenter + sliderHeight * 3 / 2,
            paint
        )
        canvas.save()
        paint.textSize = valueTextSize
        paint.isFakeBoldText = true
        paint.color = Color.rgb(255, 149, 0)

        canvas.drawText(
            minValue.toString(),
            firstCircle,
            (paint.fontMetrics.bottom - paint.fontMetrics.top) / 2 + yCenter + (17.5f).dpToPx,
            paint
        )
        canvas.save()
        paint.color = maxValueColor
        canvas.drawText(
            maxValue.toString(),
            secondCircle,
            (paint.fontMetrics.bottom - paint.fontMetrics.top) / 2 + yCenter + (17.5f).dpToPx,
            paint
        )


    }


}