@file:Suppress("NOTHING_TO_INLINE")

package me.reezy.cosmo.utility

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment


/** Context */
fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

fun Context.toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, resId, duration).show()
}


/** Fragment */
inline fun Fragment.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) = requireContext().toast(text, duration)
inline fun Fragment.toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) = requireContext().toast(resId, duration)


/** Dialog */
inline fun Dialog.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) = context.toast(text, duration)
inline fun Dialog.toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) = context.toast(resId, duration)

/** View */
inline fun View.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) = context.toast(text, duration)
inline fun View.toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) = context.toast(resId, duration)
