package io.github.xtvj.common.utils

import android.annotation.SuppressLint
import io.github.xtvj.common.utils.LogUtils.logs
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object DateUtil {

    fun formatToFull(time: Long): String {
        val date = Date(time)
        @SuppressLint("SimpleDateFormat") val simpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return simpleDateFormat.format(date)
    }

    fun formatToFullDayText(time: Long): String {
        val date = Date(time)
        @SuppressLint("SimpleDateFormat") val simpleDateFormat =
            SimpleDateFormat("yyyy年MM月dd日")
        return simpleDateFormat.format(date)
    }

    fun formatTimeToLong(time: String): Long? {
        //2022/09/09 18:03:51
        @SuppressLint("SimpleDateFormat") val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        try {
            val date = simpleDateFormat.parse(time)
            return date?.time
        } catch (e: ParseException) {
            e.localizedMessage?.let { logs(it) }
        }
        return 0L
    }

    fun formatShortToTime(time: Long): String {
        if (time < 0) {
            return "00:00"
        }
        val second: Long = time / 1000 % 60
        val minute: Long = time / (1000 * 60) % 60
        return String.format("%02d:%02d", minute, second)
    }

    fun formatLongToTime(time: Long): String {
        if (time < 0) {
            return "00:00:00"
        }
        val second: Long = time / 1000 % 60
        val minute: Long = time / (1000 * 60) % 60
        val hour: Long = time / (1000 * 60 * 60) % 60
        return String.format("%02d:%02d:%02d", hour, minute, second)
    }

    fun isSameDay(oneTime: Long, twoTime: Long): Boolean {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.time = Date(oneTime)
        cal2.time = Date(twoTime)
        return cal1[Calendar.YEAR] == cal2[Calendar.YEAR] &&
                cal1[Calendar.DAY_OF_YEAR] == cal2[Calendar.DAY_OF_YEAR]
    }

    fun getYearMonthAndDay(time: Long): Calendar {
        val cal1 = Calendar.getInstance()
        cal1.time = Date(time)
        return cal1
    }

}