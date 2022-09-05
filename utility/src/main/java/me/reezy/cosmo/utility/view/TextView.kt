package me.reezy.cosmo.utility.view

import android.text.Editable
import android.text.SpannableStringBuilder
import android.widget.TextView
import androidx.core.text.HtmlCompat

var TextView.html
    get() = HtmlCompat.toHtml(SpannableStringBuilder(text), HtmlCompat.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL)
    set(value) {
        text = HtmlCompat.fromHtml(value, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

fun Editable?.stringValue(): String {
    this ?: return ""
    return trim().toString()
}

fun TextView.stringValue(): String {
    return text.toString().trim()
}

fun TextView.floatValue(): Float {
    return if (text.isBlank()) 0f else try { text.toString().trim().toFloat()} catch(e: NumberFormatException) {
        Float.NaN}
}

fun TextView.doubleValue(): Double {
    return if (text.isBlank()) 0.0 else try { text.toString().trim().toDouble()} catch(e: NumberFormatException) {
        Double.NaN}
}

fun TextView.intValue(): Int {
    return if (text.isBlank()) 0 else text.toString().trim().toInt()
}

fun TextView.longValue(): Long {
    return if (text.isBlank()) 0 else text.toString().trim().toLong()
}