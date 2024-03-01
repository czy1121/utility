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

inline val Int.dp: Int get() = Resources.getSystem().dp(this)
inline val Int.sp: Int get() = Resources.getSystem().sp(this)
inline val Float.dp: Float get() = Resources.getSystem().dp(this)
inline val Float.sp: Float get() = Resources.getSystem().sp(this)

inline fun Context.dp(value: Int): Int = resources.dp(value)
inline fun Context.sp(value: Int): Int = resources.sp(value)
inline fun Context.dp(value: Float): Float = resources.dp(value)
inline fun Context.sp(value: Float): Float = resources.sp(value)

inline fun Fragment.dp(value: Int): Int = resources.dp(value)
inline fun Fragment.sp(value: Int): Int = resources.sp(value)
inline fun Fragment.dp(value: Float): Float = resources.dp(value)
inline fun Fragment.sp(value: Float): Float = resources.sp(value)


inline fun View.dp(value: Int): Int = resources.dp(value)
inline fun View.sp(value: Int): Int = resources.sp(value)
inline fun View.dp(value: Float): Float = resources.dp(value)
inline fun View.sp(value: Float): Float = resources.sp(value)

fun Resources.dp(value: Int): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), displayMetrics).toInt()
fun Resources.sp(value: Int): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value.toFloat(), displayMetrics).toInt()
fun Resources.dp(value: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, displayMetrics)
fun Resources.sp(value: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, displayMetrics)


// ========== px2dp/px2sp ==========


inline fun Context.px2dp(@Px px: Int) = resources.px2dp(px)
inline fun Context.px2sp(@Px px: Int) = resources.px2dp(px)

inline fun Fragment.px2dp(@Px px: Int) = resources.px2dp(px)
inline fun Fragment.px2sp(@Px px: Int) = resources.px2dp(px)

inline fun View.px2dp(@Px px: Int) = resources.px2dp(px)
inline fun View.px2sp(@Px px: Int) = resources.px2dp(px)

fun Resources.px2dp(@Px px: Int): Float = px / displayMetrics.density
fun Resources.px2sp(@Px px: Int): Float = px / displayMetrics.scaledDensity







