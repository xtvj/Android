package io.github.xtvj.common.utils

import android.content.res.Resources
import android.util.TypedValue


object ScreenUtils {

    val Float.dpToPx get() = this * Resources.getSystem().displayMetrics.density

    val Float.pxToDp get() = this / Resources.getSystem().displayMetrics.density

    val Float.spToPx get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics)

    val Int.dpToPx get() = (this * Resources.getSystem().displayMetrics.density)

    val Int.pxToDp get() = (this / Resources.getSystem().displayMetrics.density)

    val Int.spToPx get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), Resources.getSystem().displayMetrics)



}