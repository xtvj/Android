package io.github.xtvj.android.start

import android.content.Context
import androidx.startup.Initializer
import io.github.xtvj.common.utils.CoilHolder
import io.github.xtvj.common.utils.LogUtils.initLog

class StartInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        //初始化log工具
        initLog()
        CoilHolder.init(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}