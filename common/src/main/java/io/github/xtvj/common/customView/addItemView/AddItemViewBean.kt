package io.github.xtvj.common.customView.addItemView

/**
 * @param order 预置点序号 从0开始
 * @param select 当前是否被选择
 */
open class AddItemViewBean(
    open var order: Int,
    var select: Boolean = false
)
