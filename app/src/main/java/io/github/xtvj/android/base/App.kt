package io.github.xtvj.android.base

import android.app.Application
import android.content.Context
import com.skydoves.sandwich.SandwichInitializer
import dagger.hilt.android.HiltAndroidApp
import io.github.xtvj.common.utils.LogUtils.logs
import io.github.xtvj.network.api.GlobalResponseOperator
import io.github.xtvj.network.common.ConstantUtils
import io.github.xtvj.network.interfaces.LogoutListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltAndroidApp
class App : Application(), LogoutListener {

    override fun onCreate() {
        super.onCreate()

        SandwichInitializer.sandwichOperators += GlobalResponseOperator<Any>(this)
    }

    //通用退出登录数据处理操作
    private suspend fun logoutByError(context: Context) = withContext(Dispatchers.IO) {
//        PushHelper.disablePush(context)
//        dataStoreManager.insertUser("", "")
//        Constants.userId = ""
        ConstantUtils.authorization = ""
//        appDatabase.clearAllTables()
//        val intent = Intent(context, LoginActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        ContextCompat.startActivity(context, intent, null)
    }

    override fun logout(error: String) {
        MainScope().launch{
            logs(error)
            logoutByError(this@App.applicationContext)
        }
    }
}