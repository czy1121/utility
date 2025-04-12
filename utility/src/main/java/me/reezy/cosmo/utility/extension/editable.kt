package me.reezy.cosmo.utility.extension

import android.text.Editable
import android.text.Spanned

fun Editable.setText(text: String) {
    replace(0, length, text)
}

fun Editable.setStyle(span: Any) {
    setSpan(span, 0, length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
}

fun Editable.updateNumber(min: Int, max: Int, delta: Int = 0) {
    val value = toString()
    val newValue = ((value.toIntOrNull() ?: 0) + delta).coerceIn(min, max).toString()
    if (value != newValue) {
        setText(newValue)
    }
}
