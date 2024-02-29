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

inline val Number.dp: Float get() = Resources.getSystem().dp(this)
inline val Number.sp: Float get() = Resources.getSystem().sp(this)
inline fun Context.dp(value: Number): Float = resources.dp(value)
inline fun Context.sp(value: Number): Float = resources.sp(value)
inline fun Fragment.dp(value: Number): Float = resources.dp(value)
inline fun Fragment.sp(value: Number): Float = resources.sp(value)
inline fun View.dp(value: Number): Float = resources.dp(value)
inline fun View.sp(value: Number): Float = resources.sp(value)


// ========== px2dp/px2sp ==========


inline fun Context.px2dp(@Px px: Int) = resources.px2dp(px)
inline fun Context.px2sp(@Px px: Int) = resources.px2dp(px)

inline fun Fragment.px2dp(@Px px: Int) = resources.px2dp(px)
inline fun Fragment.px2sp(@Px px: Int) = resources.px2dp(px)

inline fun View.px2dp(@Px px: Int) = resources.px2dp(px)
inline fun View.px2sp(@Px px: Int) = resources.px2dp(px)


// ========== dp/sp/px2dp/px2sp ==========


fun Resources.dp(value: Number): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), displayMetrics)
fun Resources.sp(value: Number): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value.toFloat(), displayMetrics)
fun Resources.px2dp(@Px px: Int): Float = px / displayMetrics.density
fun Resources.px2sp(@Px px: Int): Float = px / displayMetrics.scaledDensity







