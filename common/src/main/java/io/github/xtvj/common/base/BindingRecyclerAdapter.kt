package io.github.xtvj.common.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BindingRecyclerAdapter<T : ViewDataBinding, A : Any> constructor(
    @LayoutRes private val contentLayoutId: Int,
    var listData: MutableList<A> = mutableListOf()
) : RecyclerView.Adapter<BindingViewHolder<T>>() {

    private var bindingComponent: DataBindingComponent? = DataBindingUtil.getDefaultComponent()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<T> {
        val viewDataBinding = DataBindingUtil.inflate<T>(
            LayoutInflater.from(parent.context),
            contentLayoutId,
            parent,
            false,
            bindingComponent
        )
        return BindingViewHolder<T>(viewDataBinding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<T>, position: Int) {
        bind(holder.binder, listData[position], position)
    }

    abstract fun bind(binding: T, item: A, position: Int)


}