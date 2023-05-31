package io.github.xtvj.android.customView

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import io.github.xtvj.android.R

class DampingReboundFrameLayout : FrameLayout {


    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(attrs)
    }


    private var mPrinceView: View? = null // 太子View
    private var mInitTop = 0
    private var mInitBottom = 0
    private var mInitLeft = 0
    private var mInitRight = 0 // 太子View初始时上下坐标位置(相对父View,

    // 即当前ReboundEffectsView)
    private var isEndwiseSlide = false // 是否纵向滑动
    private var mVariableY = 0f // 手指上下滑动Y坐标变化前的Y坐标值
    private var mVariableX = 0f // 手指上下滑动X坐标变化前的X坐标值
    private var orientation = 1//1:竖向滚动 2：横向滚动


    private fun initView(attrs: AttributeSet?) {
        this.isClickable = true
        val ta = context.obtainStyledAttributes(attrs, R.styleable.DampingReboundFrameLayout)
        orientation = ta.getInt(R.styleable.DampingReboundFrameLayout_orientation, 1)
        ta.recycle()
    }

    /**
     * Touch事件
     */
    override fun onTouchEvent(e: MotionEvent): Boolean {
        if (null != mPrinceView) {
            when (e.action) {
                MotionEvent.ACTION_DOWN -> onActionDown(e)
                MotionEvent.ACTION_MOVE -> return onActionMove(e)
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> onActionUp(e) // 当ACTION_UP一样处理
            }
        }
        return super.onTouchEvent(e)
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

    /**
     * 手指按下事件
     */
    private fun onActionDown(e: MotionEvent) {
        mVariableY = e.y
        mVariableX = e.x
        /**
         * 保存mPrinceView的初始上下高度位置
         */
        mInitTop = mPrinceView!!.top
        mInitBottom = mPrinceView!!.bottom
        mInitLeft = mPrinceView!!.left
        mInitRight = mPrinceView!!.right
    }

    /**
     * 手指滑动事件
     */
    private fun onActionMove(e: MotionEvent): Boolean {
        val nowY = e.y
        val diffY = (nowY - mVariableY) / 2
        if (orientation == 1 && Math.abs(diffY) > 0) { // 上下滑动
            // 移动太子View的上下位置
            mPrinceView!!.layout(
                mPrinceView!!.left, mPrinceView!!.top + diffY.toInt(),
                mPrinceView!!.right, mPrinceView!!.bottom + diffY.toInt()
            )
            mVariableY = nowY
            isEndwiseSlide = true
            return true // 消费touch事件
        }
        val nowX = e.x
        val diffX = (nowX - mVariableX) / 5 //除数越大可以滑动的距离越短
        if (orientation == 2 && Math.abs(diffX) > 0) { // 左右滑动
            // 移动太子View的左右位置
            mPrinceView!!.layout(
                mPrinceView!!.left + diffX.toInt(), mPrinceView!!.top,
                mPrinceView!!.right + diffX.toInt(), mPrinceView!!.bottom
            )
            mVariableX = nowX
            isEndwiseSlide = true
            return true // 消费touch事件
        }
        return super.onTouchEvent(e)
    }

    /**
     * 手指释放事件
     */
    private fun onActionUp(e: MotionEvent) {
        if (isEndwiseSlide) { // 是否为纵向滑动事件
            // 是纵向滑动事件，需要给太子View重置位置
            if (orientation == 1) {
                resetPrinceViewV()
            } else if (orientation == 2) {
                resetPrinceViewH()
            }
            isEndwiseSlide = false
        }
    }

    /**
     * 回弹，重置太子View初始的位置
     */
    private fun resetPrinceViewV() {
        val ta = TranslateAnimation(0f, 0f, (mPrinceView!!.top - mInitTop).toFloat(), 0f)
        ta.duration = 600
        mPrinceView!!.startAnimation(ta)
        mPrinceView!!.layout(mPrinceView!!.left, mInitTop, mPrinceView!!.right, mInitBottom)
    }

    private fun resetPrinceViewH() {
        val ta = TranslateAnimation((mPrinceView!!.left - mInitLeft).toFloat(), 0f, 0f, 0f)
        ta.duration = 600
        mPrinceView!!.startAnimation(ta)
        mPrinceView!!.layout(mInitLeft, mPrinceView!!.top, mInitRight, mPrinceView!!.bottom)
    }

    /**
     * XML布局完成加载
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 0) {
            mPrinceView = getChildAt(0) // 获得子View，太子View
        }
    }
}