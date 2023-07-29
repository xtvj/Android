package io.github.xtvj.common.utils

import java.text.DecimalFormat
import java.util.Locale

object Formatter {

    /**
     * get file format size * *
     * @param roundedBytes file size *
     * @return file format size (like 2.12k)
     */
    fun formatFileSize(roundedBytes: Long, shorter: Boolean): String {
        return formatFileSize(roundedBytes, shorter, Locale.US)
    }

    fun formatFileSize(roundedBytes: Long, locale: Locale, shorter: Boolean): String {
        return formatFileSize(roundedBytes, shorter, locale)
    }

    private fun formatFileSize(roundedBytes: Long, shorter: Boolean, locale: Locale): String {
        var result = roundedBytes.toFloat()
        var suffix = "B"
        if (result > 900) {
            suffix = "KB"
            result /= 1024
        }
        if (result > 900) {
            suffix = "MB"
            result /= 1024
        }
        if (result > 900) {
            suffix = "GB"
            result /= 1024
        }
        if (result > 900) {
            suffix = "TB"
            result /= 1024
        }
        if (result > 900) {
            suffix = "PB"
            result /= 1024
        }
        val value: String = if (result < 1) {
            String.format(locale, "%.2f", result)
        } else if (result < 10) {
            if (shorter) {
                String.format(locale, "%.1f", result)
            } else {
                String.format(locale, "%.2f", result)
            }
        } else if (result < 100) {
            if (shorter) {
                String.format(locale, "%.0f", result)
            } else {
                String.format(locale, "%.2f", result)
            }
        } else {
            String.format(locale, "%.0f", result)
        }
        return String.format("%s%s", value, suffix)
    }

    /**
     * 格式化文件大小
     *
     * @param fileS
     * @return
     */
    fun formatFileSize(fileS: Long): String { //转换文件大小
        val df = DecimalFormat("#.00")
        var fileSizeString = ""
        fileSizeString = if (fileS < 1024) {
            df.format(fileS.toDouble()) + "B"
        } else if (fileS < 1048576) {
            df.format(fileS.toDouble() / 1024) + "K"
        } else if (fileS < 1073741824) {
            df.format(fileS.toDouble() / 1048576) + "M"
        } else {
            df.format(fileS.toDouble() / 1073741824) + "G"
        }
        return fileSizeString
    }
}