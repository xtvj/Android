package io.github.xtvj.android.ui.blueTooth

import android.annotation.SuppressLint
import android.bluetooth.le.ScanResult
import io.github.xtvj.android.R
import io.github.xtvj.android.base.BindingRecyclerAdapter
import io.github.xtvj.android.databinding.AdapterBluetoothListBinding
import io.github.xtvj.android.utils.LogUtils.logs

internal class ScanResultsAdapter : BindingRecyclerAdapter<AdapterBluetoothListBinding, ScanResult>(R.layout.adapter_bluetooth_list) {

    var onItemClick: ((ScanResult) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged", "MissingPermission")
    fun addScanResult(bleScanResult: ScanResult) {
        listData.withIndex()
            .firstOrNull { it.value.device.address == bleScanResult.device.address }
            ?.let {
                // device already in data list => update
                listData[it.index] = bleScanResult
                notifyItemChanged(it.index)
            }
            ?: run {
                if (!bleScanResult.device.name.isNullOrBlank()){
                    with(listData) {
                        add(bleScanResult)
                        sortBy { it.device.name }
                    }
                    logs("添加蓝牙到列表：name:${bleScanResult.device.name?:"空数据"}---mac:${bleScanResult.device.address}")
                    notifyDataSetChanged()
                }
            }
    }

    override fun bind(binding: AdapterBluetoothListBinding, item: ScanResult, position: Int) {
        with(item) {
            binding.tvBlueToothName.text = String.format("%s---", scanRecord?.deviceName)
            binding.tvBlueToothRSSi.text = String.format("%s  rssi: %d", device.address,rssi)
        }
        binding.root.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
       return listData.size
    }

}
