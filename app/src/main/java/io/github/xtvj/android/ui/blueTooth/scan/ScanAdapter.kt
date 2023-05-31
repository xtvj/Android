package io.github.xtvj.android.ui.blueTooth.scan

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import io.github.xtvj.android.R
import io.github.xtvj.android.base.BindingRecyclerAdapter
import io.github.xtvj.android.databinding.AdapterBluetoothListBinding

class ScanAdapter : BindingRecyclerAdapter<AdapterBluetoothListBinding, BluetoothDevice>(R.layout.adapter_bluetooth_list) {

    var onItemClick: ((BluetoothDevice) -> Unit)? = null

    fun addScanResult(bleScanResult: BluetoothDevice) {
        listData.find {
            it.address == bleScanResult.address
        }.apply {
            if (this == null) {
                listData.add(bleScanResult)
                notifyItemInserted(listData.size)
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun bind(binding: AdapterBluetoothListBinding, item: BluetoothDevice, position: Int) {
        binding.tvBlueToothName.text = item.name
        binding.tvBlueToothRSSi.text = item.address
        binding.root.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }


}
