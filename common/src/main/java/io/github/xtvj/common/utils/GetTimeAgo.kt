package io.github.xtvj.common.utils



object GetTimeAgo {
    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS

    /**
     * 按照毫秒来存储
     *
     * @param time
     * @return
     */
    fun getTimeAgo(time: Long): String {
        var time = time
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000
        }
        val now = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return "未知时间"
        }
        val diff = now - time
        return if (diff < MINUTE_MILLIS) {
            "刚刚"
        } else if (diff < 2 * MINUTE_MILLIS) {
            "1分钟前"
        } else if (diff < 50 * MINUTE_MILLIS) {
            "${diff/ MINUTE_MILLIS}分钟前"
        } else if (diff < 90 * MINUTE_MILLIS) {
            "1小时前"
        } else if (diff < 24 * HOUR_MILLIS) {
            "${diff/ HOUR_MILLIS}小时前"
        } else if (diff < 48 * HOUR_MILLIS) {
            "昨天"
        } else {
             "${diff/ DAY_MILLIS}天前"
        }
    }

    /**
     * 按照毫秒来存储
     *
     * @param time
     * @return
     */
    fun getTimeAgoAlert(time: Long): String {
        var time = time
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000
        }
        val now = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return "未知时间"
        }
        val diff = now - time
        return if (diff < MINUTE_MILLIS) {
            "刚刚"
        } else if (diff < 2 * MINUTE_MILLIS) {
            "1分钟前"
        } else if (diff < 50 * MINUTE_MILLIS) {
            "${diff/ MINUTE_MILLIS}分钟前"
        } else if (diff < 90 * MINUTE_MILLIS) {
            "1小时前"
        } else if (diff < 24 * HOUR_MILLIS) {
            "${diff/ HOUR_MILLIS}小时前"
        }else if (diff < 48 * HOUR_MILLIS) {
            "昨天"
        } else {
             DateUtil.formatToFull(time)
        }
    }
}