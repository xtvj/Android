package io.github.xtvj.common.utils

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.DrawableRes

object EditViewUtils {


    /**
     * @param eyes 显示密码时的图标
     * @param close 不显示密码时的图标
     */
    fun showHidePassWhiteIcon(
        edit: EditText,
        iv: ImageView,
        @DrawableRes eyes: Int,
        @DrawableRes close: Int
    ) {
        if (edit.transformationMethod.equals(PasswordTransformationMethod.getInstance())) {
            iv.setImageResource(eyes)
            edit.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            iv.setImageResource(close)
            edit.transformationMethod = PasswordTransformationMethod.getInstance()
        }
        edit.setSelection(edit.text?.length ?: 0)
    }
}