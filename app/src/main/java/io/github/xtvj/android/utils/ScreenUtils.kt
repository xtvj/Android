package io.github.xtvj.android.utils

import android.content.res.Resources
import android.util.TypedValue

object ScreenUtils {

    val Float.toPx get() = this * Resources.getSystem().displayMetrics.density

    val Float.toDp get() = this / Resources.getSystem().displayMetrics.density

    val Int.toPx get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    val Int.toDp get() = (this / Resources.getSystem().displayMetrics.density).toInt()

}