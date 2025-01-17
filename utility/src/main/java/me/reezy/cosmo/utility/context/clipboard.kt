package me.reezy.cosmo.utility.context

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.ContextCompat

inline val Context.clipboardManager: ClipboardManager? get() = ContextCompat.getSystemService(this, ClipboardManager::class.java)

var Context.clipboardPrimaryText: String
    get() = clipboardManager?.primaryClip?.let {
        if (it.itemCount > 0) it.getItemAt(0).text.toString() else null
    } ?: ""
    set(content) {
        clipboardManager?.setPrimaryClip(ClipData.newPlainText(null, content))
    }