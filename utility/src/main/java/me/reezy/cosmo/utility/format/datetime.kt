
@file:Suppress("NOTHING_TO_INLINE")

package me.reezy.cosmo.utility.format

import android.annotation.SuppressLint
import android.content.Context
import me.reezy.cosmo.R
import java.text.SimpleDateFormat
import java.util.*


val TIMEZONE_D: TimeZone get() = TimeZone.getDefault()
val TIMEZONE_0: TimeZone = TimeZone.getTimeZone("GMT+0")
val TIMEZONE_8: TimeZone = TimeZone.getTimeZone("GMT+8")

@SuppressLint("SimpleDateFormat")
internal val formatter = SimpleDateFormat()

fun Date.format(pattern: String, timezone: TimeZone = TIMEZONE_D): String = synchronized(formatter) {
    formatter.timeZone = timezone
    formatter.applyPattern(pattern)
    return formatter.format(this)
}
inline fun Date.formatDateTimeMs(timezone: TimeZone = TIMEZONE_D): String = format("yyyy-MM-dd HH:mm:ss.SSS", timezone)
inline fun Date.formatDateTime(timezone: TimeZone = TIMEZONE_D): String = format("yyyy-MM-dd HH:mm:ss", timezone)
inline fun Date.formatDate(timezone: TimeZone = TIMEZONE_D): String = format("yyyy-MM-dd", timezone)
inline fun Date.formatShortDate(timezone: TimeZone = TIMEZONE_D): String = format("MM-dd", timezone)
inline fun Date.formatTime(timezone: TimeZone = TIMEZONE_D): String = format("HH:mm:ss", timezone)
inline fun Date.formatShortTime(timezone: TimeZone = TIMEZONE_D): String = format("HH:mm", timezone)

inline fun Long.format(pattern: String, timezone: TimeZone = TIMEZONE_D): String = Date(this).format(pattern, timezone)
inline fun Long.formatDateTimeMs(timezone: TimeZone = TIMEZONE_D): String = Date(this).format("yyyy-MM-dd HH:mm:ss.SSS", timezone)
inline fun Long.formatDateTime(timezone: TimeZone = TIMEZONE_D): String = Date(this).format("yyyy-MM-dd HH:mm:ss", timezone)
inline fun Long.formatDate(timezone: TimeZone = TIMEZONE_D): String = Date(this).format("yyyy-MM-dd", timezone)
inline fun Long.formatShortDate(timezone: TimeZone = TIMEZONE_D): String = Date(this).format("MM-dd", timezone)
inline fun Long.formatTime(timezone: TimeZone = TIMEZONE_D): String = Date(this).format("HH:mm:ss", timezone)
inline fun Long.formatShortTime(timezone: TimeZone = TIMEZONE_D): String = Date(this).format("HH:mm", timezone)


const val MS_SECOND = 1000L
const val MS_MINUTE = 60 * 1000L
const val MS_HOUR = 3600 * 1000L
const val MS_HALF_DAY = 12 * MS_HOUR
const val MS_DAY = 24 * MS_HOUR
const val MS_WEEKLY = 7 * MS_DAY
const val MS_MONTH = 30 * MS_DAY
const val MS_YEAR = 365 * MS_DAY


fun Long.formatCountdown(pattern: String = "HH:mm:ss") = Date(this).format(pattern, TIMEZONE_0)

/**
 *
 * ???????????????????????????
 * ?????????????????????xx??????
 * ?????????????????????xx?????????
 * ??????????????????xx?????????
 * ??????????????????xx??????
 * ??????????????? 2019-05-05 11:11
 *
 * */
fun Long.formatTimeAgo(context: Context, years: Boolean = true): String {
    val resources = context.resources
    val span = System.currentTimeMillis() - this
    return when {
        span < 30 * MS_SECOND -> resources.getString(R.string.format_time_ago_just)
        span < MS_MINUTE -> resources.getString(R.string.format_time_ago_seconds, span / MS_SECOND)
        span < MS_HOUR -> resources.getString(R.string.format_time_ago_minutes, span / MS_MINUTE)
        span < MS_DAY -> resources.getString(R.string.format_time_ago_hours, span / MS_HOUR)
        span < MS_MONTH -> resources.getString(R.string.format_time_ago_days, span / MS_DAY)
        span < MS_YEAR -> resources.getString(R.string.format_time_ago_days, span / MS_MONTH)
        years -> resources.getString(R.string.format_time_ago_days, span / MS_MONTH)
        else -> Date(this).format("yyyy-MM-dd HH:mm")
    }
}

/**
 * ?????? 9:11, ?????? 4:22
 * ?????? ?????? 9:11, ?????? ?????? 4:22
 * ?????? ?????? 9:11, ?????? ?????? 4:22
 * 5???21??? ?????? 9:11, 5???21??? ?????? 4:22
 * 2018???5???21??? ?????? 9:11, 2018???5???21??? ?????? 4:22
 *
 * */
fun Long.formatTimeChat(): String {
    val c = Calendar.getInstance()
    c.timeInMillis = this
    val c1 = Calendar.getInstance()

    return when {
        c1.timeInMillis - this < MS_MINUTE -> ""
        c1[Calendar.DAY_OF_YEAR] == c[Calendar.DAY_OF_YEAR] -> Date(this).format("aH:m")
        c1[Calendar.DAY_OF_YEAR] == c[Calendar.DAY_OF_YEAR] + 1 -> Date(this).format("?????? aH:m")
        c1[Calendar.WEEK_OF_YEAR] == c[Calendar.WEEK_OF_YEAR] -> Date(this).format("E aH:m")
        c1[Calendar.YEAR] == c[Calendar.YEAR] -> Date(this).format("M???d??? aH:m")
        else -> Date(this).format("yyyy???M???d??? aH:m")
    }
}