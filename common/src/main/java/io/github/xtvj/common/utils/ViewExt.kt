package io.github.xtvj.common.utils

import android.widget.ImageView
import coil.load
import io.github.xtvj.common.R


fun ImageView.loadImageWithError(
    resId: Any?,
    errorId: Int
) {
    this.load(resId ?: errorId) {
        crossfade(false)
        placeholder(R.drawable.icon_pic_rig)
        error(errorId)
    }
}