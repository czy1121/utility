package me.reezy.cosmo.utility

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment


/** Context */
fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT, gravity: Int = Gravity.CENTER) {
    val t = Toast.makeText(this, text, duration)
    t.setGravity(gravity, 0, 0)
    t.show()
}

fun Context.toast(resId: Int, duration: Int = Toast.LENGTH_SHORT, gravity: Int = Gravity.CENTER) {
    val t = Toast.makeText(this, resId, duration)
    t.setGravity(gravity, 0, 0)
    t.show()
}


/** Fragment */
inline fun Fragment.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT, gravity: Int = Gravity.CENTER) {
    requireContext().toast(text, duration, gravity)
}
inline fun Fragment.toast(resId: Int, duration: Int = Toast.LENGTH_SHORT, gravity: Int = Gravity.CENTER) {
    requireContext().toast(resId, duration, gravity)
}


/** Dialog */
inline fun Dialog.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT, gravity: Int = Gravity.CENTER) {
    context.toast(text, duration, gravity)
}
inline fun Dialog.toast(resId: Int, duration: Int = Toast.LENGTH_SHORT, gravity: Int = Gravity.CENTER) {
    context.toast(resId, duration, gravity)
}

/** View */
inline fun View.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT, gravity: Int = Gravity.CENTER) {
    context.toast(text, duration, gravity)
}
inline fun View.toast(resId: Int, duration: Int = Toast.LENGTH_SHORT, gravity: Int = Gravity.CENTER) {
    context.toast(resId, duration, gravity)
}
