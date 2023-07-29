package io.github.xtvj.common.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

abstract class BindingPagingDataWithHolderAdapter<T : ViewDataBinding, A : Any, H : BindingViewHolder<T>> constructor(
    @LayoutRes private val contentLayoutId: Int,
    callback: DiffUtil.ItemCallback<A>
) : PagingDataAdapter<A, H>(callback) {

    private var bindingComponent: DataBindingComponent? = DataBindingUtil.getDefaultComponent()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H {
        val viewDataBinding = DataBindingUtil.inflate<T>(
            LayoutInflater.from(parent.context),
            contentLayoutId,
            parent,
            false,
            bindingComponent
        )
        return createHolder(viewDataBinding)
    }

    abstract fun createHolder(binding: T): H
}