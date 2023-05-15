package io.github.xtvj.android.utils

import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable

object MyDrawableUtils {
    fun startAnimate(drawable: Drawable) {
        if (drawable is Animatable) {
            with(drawable as Animatable) {
                if (isRunning) {
                    stop()
                } else {
                    start()
                }
            }
        }
    }
}