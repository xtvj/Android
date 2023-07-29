package io.github.xtvj.common.customView

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout

/**
 * 等宽tabLayout，需要设置
 * app:tabMode="scrollable"
 * app:tabMaxWidth="0dp"
 */
class MonospaceTabLayout : TabLayout {

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val tabLayout = getChildAt(0) as ViewGroup
        if (tabLayout.childCount > 0) {
            val widthPixels = MeasureSpec.getSize(widthMeasureSpec)
            val tabMinWidth = widthPixels / tabLayout.childCount
            for (item in 0 until tabLayout.childCount) {
                tabLayout.getChildAt(item).minimumWidth = tabMinWidth
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}