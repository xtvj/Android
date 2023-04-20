package io.github.xtvj.android.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class SleepStatusView : View {

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)

    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr)

    /**
     * 睡眠状态
     * @property wideAwake 清醒
     * @property shallowSleep 浅睡
     * @property middleSleep 中睡
     * @property deepSleep 深睡
     */
    enum class SleepStatus {
        wideAwake, shallowSleep, middleSleep, deepSleep
    }

    /**
     * @property lengthPercent 睡眠时长所占决时长百分比
     */
    data class SleepStatusList(
        var status: SleepStatus = SleepStatus.wideAwake,
        var lengthPercent: Float = 0f,
    ) {
       fun getStatusHeight(): Float {
            return when (status) {
                SleepStatus.wideAwake -> {
                    0.75f
                }
                SleepStatus.shallowSleep -> {
                    0.5f
                }
                SleepStatus.middleSleep -> {
                    0.25f
                }
                SleepStatus.deepSleep -> {
                    0f
                }
            }
        }
        fun getStatusColor():Int{
            return when (status) {
                SleepStatus.wideAwake -> {
                    Color.rgb(26, 32, 46)
                }
                SleepStatus.shallowSleep -> {
                    Color.rgb(255, 149, 0)
                }
                SleepStatus.middleSleep -> {
                    Color.rgb(3, 165, 101)
                }
                SleepStatus.deepSleep -> {
                    Color.rgb(13, 105, 248)
                }
            }
        }
    }

//    var list = listOf<SleepStatusList>(
//        SleepStatusList(SleepStatus.wideAwake,0.15f),
//        SleepStatusList(SleepStatus.shallowSleep,0.25f),
//        SleepStatusList(SleepStatus.deepSleep,0.16f),
//        SleepStatusList(SleepStatus.middleSleep,0.38f),
//        SleepStatusList(SleepStatus.wideAwake,0.06f),
//    )
    var list = emptyList<SleepStatusList>()
        set(value) {
            field = value
            invalidate()
        }

    private val paint = Paint()
    private var start = 0f

    init {
        paint.strokeWidth = 1f
        paint.color = Color.rgb(255, 55, 81)
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.SQUARE
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        start = 0f
        list.forEach { item ->
            paint.color = item.getStatusColor()
            canvas?.drawRect(start * width, height * item.getStatusHeight(), (start + item.lengthPercent) * width, height.toFloat(), paint)
            start += item.lengthPercent
            canvas?.save()
        }
    }

}