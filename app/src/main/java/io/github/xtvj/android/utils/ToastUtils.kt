package io.github.xtvj.android.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

object ToastUtils {
    fun Context.toast(@StringRes res: Int) = Toast.makeText(this.applicationContext, res, Toast.LENGTH_SHORT).show()
    fun Context.toastLong(@StringRes res: Int) = Toast.makeText(this.applicationContext, res, Toast.LENGTH_LONG).show()

    fun Context.toast(message: CharSequence) =
        Toast.makeText(this.applicationContext, message, Toast.LENGTH_SHORT).show()

    fun Context.toastLong(message: CharSequence) =
        Toast.makeText(this.applicationContext, message, Toast.LENGTH_LONG).show()
}

