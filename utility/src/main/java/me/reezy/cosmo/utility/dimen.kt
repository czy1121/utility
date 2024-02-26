@file:Suppress("NOTHING_TO_INLINE")

package me.reezy.cosmo.utility

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import androidx.annotation.Px
import androidx.fragment.app.Fragment
import kotlin.math.roundToInt

// ========== dp/sp ==========



inline val Int.dp get() = Resources.getSystem().dp(this).toInt()
inline val Int.sp get() = Resources.getSystem().sp(this).toInt()
inline val Float.dp get() = Resources.getSystem().dp(this)
inline val Float.sp get() = Resources.getSystem().sp(this)


inline fun Context.dp(value: Int) = resources.dp(value).toInt()
inline fun Context.sp(value: Int) = resources.sp(value).toInt()
inline fun Context.dp(value: Float) = resources.dp(value)
inline fun Context.sp(value: Float) = resources.sp(value)

inline fun Fragment.dp(value: Int) = resources.dp(value).toInt()
inline fun Fragment.sp(value: Int) = resources.sp(value).toInt()
inline fun Fragment.dp(value: Float) = resources.dp(value)
inline fun Fragment.sp(value: Float) = resources.sp(value)

inline fun View.dp(value: Int) = resources.dp(value).toInt()
inline fun View.sp(value: Int) = resources.sp(value).toInt()
inline fun View.dp(value: Float) = resources.dp(value)
inline fun View.sp(value: Float) = resources.sp(value)


// ========== px2dp/px2sp ==========


inline fun Context.px2dp(@Px px: Int) = resources.px2dp(px)
inline fun Context.px2sp(@Px px: Int) = resources.px2dp(px)

inline fun Fragment.px2dp(@Px px: Int) = resources.px2dp(px)
inline fun Fragment.px2sp(@Px px: Int) = resources.px2dp(px)

inline fun View.px2dp(@Px px: Int) = resources.px2dp(px)
inline fun View.px2sp(@Px px: Int) = resources.px2dp(px)


// ========== dp/sp/px2dp/px2sp ==========


fun Resources.dp(value: Number) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), displayMetrics)
fun Resources.sp(value: Number) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value.toFloat(), displayMetrics)
fun Resources.px2dp(@Px px: Int) = px / displayMetrics.density
fun Resources.px2sp(@Px px: Int) = px / displayMetrics.scaledDensity







