package io.github.xtvj.common.customView.addItemView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import io.github.xtvj.common.R

open class InnerItemViewDelegate : AddItemView.ItemViewDelegate<InnerItemViewDelegate.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup): VH {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(
        holder: VH,
        bean: AddItemViewBean,
        addItemView: AddItemView
    ) {

        holder.ivDel.setOnClickListener {
            addItemView.onItemViewClickListener?.invoke(holder.bindingAdapterPosition,bean,0)
        }
        holder.mbPresetPoint.setOnClickListener {
            holder.itemView.performClick()
        }

    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivDel: MaterialButton = itemView.findViewById(R.id.btItemViewDelete)
        val mbPresetPoint: MaterialButton = itemView.findViewById(R.id.mbItemView)
    }
}