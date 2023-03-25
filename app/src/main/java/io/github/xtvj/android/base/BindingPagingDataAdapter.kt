package io.github.xtvj.android.base

import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

abstract class BindingPagingDataAdapter<T : ViewDataBinding, A : Any> constructor(
    @LayoutRes private val contentLayoutId: Int,
    callback: DiffUtil.ItemCallback<A>
) : PagingDataAdapter<A, BindingViewHolder<T>>(callback) {

    private var bindingComponent: DataBindingComponent? = DataBindingUtil.getDefaultComponent()

    val dm: DisplayMetrics = Resources.getSystem().displayMetrics

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
        getItem(position)?.let {
            bind(holder.binder, it, position)
        }
    }

    override fun onBindViewHolder(holder: BindingViewHolder<T>, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            getItem(position)?.let {
                bind(holder.binder, it, position)
            }
        }
    }

    abstract fun bind(binding: T, item: A, position: Int)


}