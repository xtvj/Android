package io.github.xtvj.android.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView

class GridSpaceItemDecoration
/**
 * @param spanCount// 横条目数量
 * @param rowSpacing// 行间距
 * @param columnSpacing// 列间距
 */(
    private val mSpanCount: Int,
    private val mRowSpacing: Int,
    private val mColumnSpacing: Int
) : ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (mSpanCount == 0) return
        val position = parent.getChildAdapterPosition(view)// 获取view 在adapter中的位置。
        val column = position % mSpanCount// view 所在的列
        // 左边距：column * (列间距 * (1f / 列数))
        outRect.left = column * mColumnSpacing / mSpanCount
        // 右边距：列间距 - (column + 1) * (列间距 * (1f /列数))
        outRect.right = mColumnSpacing - (column + 1) * mColumnSpacing / mSpanCount

        // 如果position > 行数，说明不是在第一行，则不指定行高，其他行的上间距为 top=mRowSpacing
        if (position >= mSpanCount) {
            outRect.top = mRowSpacing // item top
        }
    }

}