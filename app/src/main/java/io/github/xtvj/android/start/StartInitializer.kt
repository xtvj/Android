package io.github.xtvj.android.start

import android.content.Context
import androidx.startup.Initializer
import io.github.xtvj.android.utils.LogUtils.initLog

class StartInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        //初始化log工具
        initLog()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}