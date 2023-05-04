package io.github.xtvj.android.ui.blueTooth

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.pm.PackageManager
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import io.github.xtvj.android.R
import io.github.xtvj.android.base.BindingActivity
import io.github.xtvj.android.databinding.ActivityScanBlueToothBinding
import io.github.xtvj.android.utils.ToastUtils.toast
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.RuntimePermissions
import javax.inject.Inject

@AndroidEntryPoint
@RuntimePermissions
class ScanBlueToothActivity : BindingActivity<ActivityScanBlueToothBinding>(R.layout.activity_scan_blue_tooth), Toolbar.OnMenuItemClickListener {

    @Inject
    lateinit var bluetoothAdapter: BluetoothAdapter

    private val bluetoothLeScanner: BluetoothLeScanner? by lazy {
        if (bluetoothAdapter.isEnabled) {
            bluetoothAdapter.bluetoothLeScanner
        } else {
            null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.tbScanBlueTooth.apply {
            setOnMenuItemClickListener(this@ScanBlueToothActivity)
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.itemBlueToothRefresh -> {
                if (bluetoothAdapter.isEnabled) {
                    val drawable = item.icon
                    if (drawable is Animatable) {
                        drawable.start()
                    }


                    bluetoothLeScanner?.startScan(leScanCallback)

                } else {
                    toast("请打开蓝牙功能")
                }
                return true
            }
        }
        return false
    }

    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
        }
    }


//    @OnShowRationale(
//        Manifest.permission.BLUETOOTH_SCAN
//    )
//    fun generatePermission(request: PermissionRequest) {
//        MaterialAlertDialogBuilder(this)
//            .setPositiveButton("允许") { _, _ -> request.proceed() }
//            .setNegativeButton("拒绝") { _, _ -> request.cancel() }
//            .setCancelable(false)
//            .setTitle("权限申请")
//            .setMessage("要先赋予App定位才能定位")
//            .create().show()
//    }
//
//    @OnPermissionDenied(
//        Manifest.permission.BLUETOOTH_SCAN
//    )
//    fun noRecordPermission() {
//        applicationContext?.toast("没有获得定位权限")
//    }
//
//    @OnNeverAskAgain(
//        Manifest.permission.BLUETOOTH_SCAN
//    )
//    fun onNeverAsk() {
//        applicationContext?.toast("定位权限被拒绝，请赋予权限。")
//    }
//
//    @NeedsPermission(
//        Manifest.permission.BLUETOOTH_SCAN)
//    fun startLocation() {
//    }

}