package io.github.xtvj.android.utils

import timber.log.Timber
import io.github.xtvj.android.BuildConfig

object LogUtils {
    //初始化log工具
    fun initLog() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    fun logs(message: String) {
        if (BuildConfig.DEBUG) {
            Timber.d(message)
        }
    }

    fun logs(message: String, vararg args: Any?) {
        if (BuildConfig.DEBUG) {
            Timber.d(message, args)
        }
    }
}
