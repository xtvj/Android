package io.github.xtvj.android.ui.blueTooth.connect

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothGattService
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.github.xtvj.android.R
import io.github.xtvj.android.databinding.ActivityBleConnectBinding
import io.github.xtvj.android.ui.blueTooth.BlePermission
import io.github.xtvj.android.utils.ContextUtils.toast
import io.github.xtvj.android.utils.LogUtils.logs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("MissingPermission")
class BleConnectActivity : BlePermission<ActivityBleConnectBinding>(R.layout.activity_ble_connect) {

    companion object {
        const val MAC_ADDRESS = "mac_address"
    }

    private val macAddress by lazy {
        intent.getStringExtra(BleConnectActivity.MAC_ADDRESS)
    }

    private lateinit var bleDevice: BluetoothDevice
    private var mBluetoothGatt: BluetoothGatt? = null
    private var service: BluetoothGattService? = null

    //写入特征值变量
    private var writecharacteristic: BluetoothGattCharacteristic? = null

    //监听特征值变量
    private var notifycharacteristic: BluetoothGattCharacteristic? = null


    override fun blueToothAction() {
        mBluetoothGatt = bleDevice.connectGatt(this@BleConnectActivity, false, mGattCallback)
    }

    override fun stopBlueToothAction() {
        mBluetoothGatt?.close()
        mBluetoothGatt = null
        lifecycleScope.launch(Dispatchers.Main) {
            binding.tbConnect.title = bleDevice.name + "(已断开)"
            loading.value = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.Main) {
            initData()
        }
    }

    private fun initData() {
        lifecycleScope.launch(Dispatchers.Main) {
            loadingMessage.value = "连接中……"
            loading.value = true
            bleDevice = mBluetoothAdapter.getRemoteDevice(macAddress)
            checkBlueToothPermission(true)
        }
    }


    private val mGattCallback: BluetoothGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            if (status == 133) {
                //断开设备
                if (mBluetoothGatt != null) {
                    mBluetoothGatt!!.disconnect()
                }
                //重新连接。
                lifecycleScope.launch(Dispatchers.Main) {
                    binding.tbConnect.title = bleDevice.name + "(重新连接中)"
                }
                blueToothAction()
            } else {
                if (BluetoothGatt.STATE_CONNECTED == newState) {
                    mBluetoothGatt?.discoverServices()
                    lifecycleScope.launch(Dispatchers.Main) {
                        binding.tbConnect.title = bleDevice.name + "(已连接)"
                    }
                } else if (BluetoothGatt.STATE_DISCONNECTED == newState) {
                    stopBlueToothAction()
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            super.onServicesDiscovered(gatt, status)
            //发现服务标识
//            service = mBluetoothGatt?.getService(UUID.fromString("在这里输入服务的ID"))
//            writecharacteristic = service?.getCharacteristic(UUID.fromString("在这里输入write的uuid"))
//            notifycharacteristic = service?.getCharacteristic(UUID.fromString("在这里输入notify的uuid"))
//            setCharacteristicNotification(notifycharacteristic!!, true)

            //关闭连接进度
            lifecycleScope.launch(Dispatchers.Main) {
                loading.value = false
            }
        }

        fun setCharacteristicNotification(
            characteristic: BluetoothGattCharacteristic,
            enable: Boolean
        ) {
            if (mBluetoothGatt != null) {
                val b: Boolean =
                    mBluetoothGatt?.setCharacteristicNotification(characteristic, enable) == true
                if (b) {
                    characteristic.writeType = BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
                }
                val descriptors = characteristic.descriptors
                for (dp in descriptors) {
                    if (dp != null) {
                        if (characteristic.properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY != 0) {
                            dp.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                        } else if (characteristic.properties and BluetoothGattCharacteristic.PROPERTY_INDICATE != 0) {
                            dp.value = BluetoothGattDescriptor.ENABLE_INDICATION_VALUE
                        }
                        mBluetoothGatt?.writeDescriptor(dp)
                    }
                }
                logs("startRead: 监听数据开始")
            } else {
                logs("服务异常")
            }
        }

        override fun onCharacteristicWrite(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            super.onCharacteristicWrite(gatt, characteristic, status)
            println("status=$status")
            if (status == BluetoothGatt.GATT_SUCCESS) {
                logs("写入成功" + characteristic.value.decodeToString())
            }
        }

//        override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, value: ByteArray) {
//            super.onCharacteristicChanged(gatt, characteristic, value)
//           //无数据返回，此方法不可用
//            logs("返回收到的数据为：$data")
//        }

        @Deprecated("Deprecated in Java")
        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic
        ) {
            super.onCharacteristicChanged(gatt, characteristic)
            logs("返回收到的数据为：${characteristic.value}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        checkBlueToothPermission(false)
    }

    /**
     * 写入数据到蓝牙设备
     */
    private fun writeData(data: ByteArray) {
        try { //拿到写的特征值
            writecharacteristic?.setValue(data)
            mBluetoothGatt?.writeCharacteristic(writecharacteristic)
        } catch (e: Exception) {
            toast("蓝牙丢失")
        }
    }

}