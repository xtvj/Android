package io.github.xtvj.android.ui.blueTooth

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Intent
import android.graphics.drawable.Animatable
import android.os.Build
import android.os.Bundle
import android.os.ParcelUuid
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import dagger.hilt.android.AndroidEntryPoint
import io.github.xtvj.android.R
import io.github.xtvj.android.base.BindingActivity
import io.github.xtvj.android.databinding.ActivityScanBlueToothBinding
import io.github.xtvj.android.utils.LogUtils.logs
import io.github.xtvj.android.utils.ContextUtils.toast
import javax.inject.Inject

@AndroidEntryPoint
class ScanBlueToothActivity : BindingActivity<ActivityScanBlueToothBinding>(R.layout.activity_scan_blue_tooth), Toolbar.OnMenuItemClickListener {

    @Inject
    lateinit var bluetoothAdapter: BluetoothAdapter
    private var isScanning = false

    private val bluetoothLeScanner: BluetoothLeScanner? by lazy {
        if (bluetoothAdapter.isEnabled) {
            bluetoothAdapter.bluetoothLeScanner
        } else {
            null
        }
    }

    private val adapter by lazy {
        ScanResultsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.tbScanBlueTooth.apply {
            setOnMenuItemClickListener(this@ScanBlueToothActivity)
        }
        binding.rvBLE.adapter = adapter
        adapter.onItemClick = {

        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.itemBlueToothRefresh -> {
                if (bluetoothAdapter.isEnabled) {
                    scanBlueTooth()
                } else {
                    toast("请打开蓝牙功能")
                }
                return true
            }
        }
        return false
    }

    private val leScanCallback: ScanCallback = object : ScanCallback() {
        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            logs("扫描到蓝牙：${result.device.name} --- ${result.device.address}")
            adapter.addScanResult(result)
        }
    }

    private fun scanBlueTooth() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requestMultiplePermissions.launch(
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT
                )
            )
        } else {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            requestBluetooth.launch(enableBtIntent)
        }
    }

//    private val scanFilter = ScanFilter.Builder().setServiceUuid(ParcelUuid.fromString("6e403587-b5a3-f393-e0a9-e50e24dcca9e")).build()
    private val scanFilter = ScanFilter.Builder().build()

    private val scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
        .build()

    @SuppressLint("MissingPermission")
    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val temp = permissions.entries.find {
                !it.value
            }
            if (temp == null) {
                if (isScanning) {
                    bluetoothLeScanner?.stopScan(leScanCallback)
                    switchToolbarMenuAnimation(false)
                } else {
                    switchToolbarMenuAnimation(true)
                    bluetoothLeScanner?.startScan(listOf(scanFilter), scanSettings, leScanCallback)
//                    bluetoothLeScanner?.startScan(leScanCallback)
                }
                isScanning = !isScanning
            }
        }

    @SuppressLint("MissingPermission")
    private var requestBluetooth = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            if (isScanning) {
                bluetoothLeScanner?.stopScan(leScanCallback)
                switchToolbarMenuAnimation(false)
            } else {
                switchToolbarMenuAnimation(true)
                bluetoothLeScanner?.startScan(listOf(scanFilter), scanSettings, leScanCallback)
            }
            isScanning = !isScanning
        }
    }

    private fun switchToolbarMenuAnimation(start: Boolean) {
        val drawable = binding.tbScanBlueTooth.menu.findItem(R.id.itemBlueToothRefresh)?.icon
        if (drawable is Animatable) {
            if (start) drawable.start() else drawable.stop()
        }
    }

}