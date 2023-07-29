package io.github.xtvj.common.customView.addItemView

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class ItemGapDecoration(
    private val itemGap: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(itemGap, itemGap, itemGap, itemGap)
    }
}