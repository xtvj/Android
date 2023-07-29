package io.github.xtvj.android.ui.blueTooth.scan

import android.annotation.SuppressLint
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Intent
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import io.github.xtvj.android.R
import io.github.xtvj.android.databinding.ActivityScanBlueToothBinding
import io.github.xtvj.android.ui.blueTooth.BlePermission
import io.github.xtvj.android.ui.blueTooth.connect.BleConnectActivity
import io.github.xtvj.common.utils.ContextUtils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("MissingPermission")
class ScanBlueToothActivity :
    BlePermission<ActivityScanBlueToothBinding>(R.layout.activity_scan_blue_tooth),
    Toolbar.OnMenuItemClickListener {

    private val adapter by lazy {
        ScanAdapter()
    }

    private var bluetoothLeScanner: BluetoothLeScanner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        lifecycleScope.launch(Dispatchers.Main) {
            initData()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initView() {

        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(resources.getDrawable(R.drawable.recyclerview_divider_8dp, null))
        binding.rvBlueTooth.addItemDecoration(itemDecorator)
        binding.rvBlueTooth.adapter = adapter
        binding.tbScanBlueTooth.apply {
            setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            setOnMenuItemClickListener(this@ScanBlueToothActivity)
        }
    }

    private fun initData() {
        adapter.onItemClick = {
            startActivity(Intent(this, BleConnectActivity::class.java).apply {
                putExtra(BleConnectActivity.MAC_ADDRESS, it.address)
            })
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.itemBlueToothRefresh -> {
                if (mBluetoothAdapter.isEnabled) {
                    checkBlueToothPermission(!isActioning)
                } else {
                    toast("请打开蓝牙")
                }
            }
        }
        return true
    }

    override fun onPause() {
        super.onPause()
        if (mBluetoothAdapter.isEnabled && isActioning) {
            checkBlueToothPermission(false)
        }
    }

    override fun blueToothAction() {
//        val serviceUUID = ParcelUuid.fromString("在这里输入要过滤的服务ID")
        bluetoothLeScanner = mBluetoothAdapter.bluetoothLeScanner
        //设置扫描过滤条件
//        val scanFilters: MutableList<ScanFilter> = ArrayList()
        //在过滤器中添加扫描指定UUID设备的条件
//        scanFilters.add(ScanFilter.Builder().setServiceUuid(serviceUUID).build())
        bluetoothLeScanner?.startScan(
            null,
            ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build(),
            scanCallback
        )
        val drawable = binding.tbScanBlueTooth.menu.findItem(R.id.itemBlueToothRefresh)?.icon
        if (drawable is Animatable && !drawable.isRunning) {
            drawable.start()
        }
    }

    override fun stopBlueToothAction() {
        bluetoothLeScanner?.stopScan(scanCallback)
        val drawable = binding.tbScanBlueTooth.menu.findItem(R.id.itemBlueToothRefresh)?.icon
        if (drawable is Animatable && drawable.isRunning) {
            drawable.stop()
        }
    }

    private val scanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            //蓝牙设备型号判断
//            var id = 0
//            try {
//                val sparseArray = result.scanRecord!!.manufacturerSpecificData
//                id = sparseArray.keyAt(0)
//            } catch (_: ArrayIndexOutOfBoundsException) {
//            }
            adapter.addScanResult(result.device)
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
        }
    }

}