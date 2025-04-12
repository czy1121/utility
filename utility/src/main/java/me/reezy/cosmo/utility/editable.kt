package me.reezy.cosmo.utility

import android.text.Editable
import android.text.Spanned
import kotlin.math.min
import kotlin.math.max

fun Editable.setText(text: String) {
    replace(0, length, text)
}

fun Editable.setSpan(span: Any) {
    setSpan(span, 0, length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
}

fun Editable.setLimit(min: Int, max: Int) {
    if (isEmpty()) {
        return
    }
    val value = toString().toIntOrNull() ?: 0
    if (value < min) {
        setText(min.toString())
    } else if (value > max) {
        setText(max.toString())
    }
}

fun Editable.add(delta: Int, min: Int, max: Int) {
    if (delta == 0) return
    val value = toString().toIntOrNull() ?: 0
    val newValue = (value + delta).coerceIn(min, max)
    setText(newValue.toString())
}
