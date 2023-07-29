package io.github.xtvj.common.customView.addItemView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import io.github.xtvj.common.R

open class InnerAddItemViewDelegate : AddItemView.AddViewDelegate<InnerAddItemViewDelegate.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup): VH {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.add_item_view, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(
        holder: VH,
        position: Int,
        addItemView: AddItemView
    ) {
        holder.mbPresetPoint.setOnClickListener {
            holder.itemView.performClick()
        }
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mbPresetPoint: MaterialButton = itemView.findViewById(R.id.mbItemViewAdd)
    }
}
