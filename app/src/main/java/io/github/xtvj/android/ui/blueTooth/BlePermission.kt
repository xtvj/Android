package io.github.xtvj.android.ui.blueTooth

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.databinding.ViewDataBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.github.xtvj.common.base.BaseActivity
import io.github.xtvj.common.utils.ContextUtils.toast
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
abstract class BlePermission<T : ViewDataBinding> constructor(
    @LayoutRes private val contentLayoutId: Int
) : BaseActivity<T>(contentLayoutId) {

    lateinit var mBluetoothAdapter: BluetoothAdapter

    //是否在扫描或别的动作中
    var isActioning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @NeedsPermission(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT)
    fun blueToothScan() {
        checkBlueToothEnable()
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun checkFineLocation() {
        checkBlueToothEnableLessS()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @OnShowRationale(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT)
    fun generatePermissionBlueTooth(request: PermissionRequest) {
        MaterialAlertDialogBuilder(applicationContext)
            .setPositiveButton("允许") { _, _ -> request.proceed() }
            .setNegativeButton("拒绝") { _, _ -> request.cancel() }
            .setCancelable(false)
            .setMessage("要先赋予App蓝牙权限")
            .create().show()
    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    fun generatePermissionGPS(request: PermissionRequest) {
        MaterialAlertDialogBuilder(applicationContext)
            .setPositiveButton("允许") { _, _ -> request.proceed() }
            .setNegativeButton("拒绝") { _, _ -> request.cancel() }
            .setCancelable(false)
            .setMessage("要先赋予App定位权限")
            .create().show()
    }

    fun checkBlueToothPermission(action:Boolean) {
        isActioning = action
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            checkFineLocationWithPermissionCheck()
        } else {
            blueToothScanWithPermissionCheck()
        }
    }

    private fun checkBlueToothEnable() {
        if (mBluetoothAdapter.isEnabled) {
            if (isActioning){
                blueToothAction()
            }else{
                stopBlueToothAction()
            }
        } else {
            blueToothEnable.launch(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        }
    }

    private fun checkBlueToothEnableLessS() {
        if (mBluetoothAdapter.isEnabled) {
            //地址位置GPS是否打开
            val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager //获取系统的定位管理器
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                gpsEnable.launch(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            }else{
                if (isActioning){
                    blueToothAction()
                }else{
                    stopBlueToothAction()
                }
            }
        } else {
            blueToothEnable.launch(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        }
    }

    private val blueToothEnable = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                checkBlueToothEnableLessS()
            } else {
                checkBlueToothEnable()
            }
        } else {
            toast("请打开蓝牙功能")
        }
    }

    private val gpsEnable = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                checkBlueToothEnableLessS()
            } else {
                checkBlueToothEnable()
            }
        } else {
            toast("请打开GPS功能")
        }
    }

    abstract fun blueToothAction()
    abstract fun stopBlueToothAction()
}