package io.github.xtvj.android.ui.customview


import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import io.github.xtvj.android.R
import io.github.xtvj.common.utils.ScreenUtils.dpToPx
import kotlin.random.Random

class PinchZoomView : View {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    private val paint = Paint()
    private val matrix = Matrix()
    private var scaleFactor = 1f

    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var m = FloatArray(9)

    private fun getTranslateX(): Float {
        matrix.getValues(m)
        return m[2]
    }

    private fun getTranslateY(): Float {
        matrix.getValues(m)
        return m[5]
    }

    private fun getMatrixScaleY(): Float {
        matrix.getValues(m)
        return m[4]
    }

    private fun getMatrixScaleX(): Float {
        matrix.getValues(m)
        return m[Matrix.MSCALE_X]
    }

    /**
     * 是否是第一次缩放
     */
    var firstScale = true

    var scaleXPinch = 0f
    var scaleYPinch: Float = 0f

    private var scaleDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.OnScaleGestureListener {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            var scaleFactor = detector.scaleFactor
            if (getMatrixScaleY() * scaleFactor > 3) {
                scaleFactor = 3 / getMatrixScaleY()
            }
            if (firstScale) {
                scaleXPinch = detector.currentSpanX
                scaleYPinch = detector.currentSpanY
                firstScale = false
            }

            if (getMatrixScaleY() * scaleFactor < 0.5) {
                scaleFactor = 0.5f / getMatrixScaleY()
            }
            matrix.postScale(scaleFactor, scaleFactor, scaleXPinch, scaleYPinch)
            invalidate()
            return true
        }

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {
            firstScale = true
        }
    })


    // 圆点半径（单位：像素）
    private val circleRadius = (10).dpToPx

    // 圆点数量
    private val circleCount = 5

    // 圆点坐标列表
    private val circlePositions = List(circleCount) {
        Pair(Random.nextInt((300).dpToPx.toInt()), Random.nextInt((300).dpToPx.toInt()))
    }

    private val pinchPic = resources.getDrawable(R.mipmap.ic_launcher,null)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        canvas.concat(matrix)
        // 在这里绘制你的内容（受缩放影响）
        pinchPic.setBounds(0,0,width,height)
        pinchPic.draw(canvas)

        // 绘制圆点（位置受缩放影响，大小不受缩放影响）
        for ((x, y) in circlePositions) {
            canvas.drawCircle(x.toFloat(), y.toFloat(), circleRadius / scaleFactor, paint)
        }
        canvas.restore()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchX = event.x
                lastTouchY = event.y

                scaleXPinch = event.x
                scaleYPinch = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                if (!scaleDetector.isInProgress) {
                    val dx = event.x - lastTouchX
                    val dy = event.y - lastTouchY

                    matrix.postTranslate(dx, dy)

                    invalidate()

                    lastTouchX = event.x
                    lastTouchY = event.y
                }
            }
            MotionEvent.ACTION_UP -> {}
            MotionEvent.ACTION_CANCEL -> {}
        }

        return true
    }

    private var gestureDetector = GestureDetector(context, object : SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            return super.onSingleTapConfirmed(e)
        }
    })
}


