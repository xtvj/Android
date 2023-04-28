package io.github.xtvj.android.utils

import android.content.res.Resources


object ScreenUtils {

    val Float.dpToPx get() = this * Resources.getSystem().displayMetrics.density

    val Float.pxToDp get() = this / Resources.getSystem().displayMetrics.density

    val Float.spToPx get() = this * Resources.getSystem().displayMetrics.scaledDensity

    val Int.dpToPx get() = (this * Resources.getSystem().displayMetrics.density)

    val Int.pxToDp get() = (this / Resources.getSystem().displayMetrics.density)

    val Int.spToPx get() = this * Resources.getSystem().displayMetrics.scaledDensity



}