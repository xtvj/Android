package io.github.xtvj.android.ui.main

import androidx.recyclerview.widget.DiffUtil
import io.github.xtvj.android.R
import io.github.xtvj.android.base.BindingPagingDataAdapter
import io.github.xtvj.android.databinding.AdapterMainBinding
import io.github.xtvj.android.utils.ScreenUtils.toPx

class MainAdapter : BindingPagingDataAdapter<AdapterMainBinding, String>(R.layout.adapter_main, diff) {

    companion object {
        val diff = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    var onItemClick: ((String, Int) -> Unit)? = null

    override fun bind(binding: AdapterMainBinding, item: String, position: Int) {
        binding.mainText = item
        val params = binding.mbAdapterMain.layoutParams
        params.width = (dm.widthPixels - (4 * 16).toPx) / 3
        binding.mbAdapterMain.setOnClickListener {
            onItemClick?.invoke(item, position)
        }
    }

}