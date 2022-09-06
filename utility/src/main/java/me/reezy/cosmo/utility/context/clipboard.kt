package me.reezy.cosmo.utility.context

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.ContextCompat


var Context.clipboardPrimaryText: String
    get() = ContextCompat.getSystemService(this, ClipboardManager::class.java)?.primaryClip?.let {
        if (it.itemCount > 0) it.getItemAt(0).text.toString() else null
    } ?: ""
    set(content) {
        ContextCompat.getSystemService(this, ClipboardManager::class.java)?.setPrimaryClip(ClipData.newPlainText(null, content))
    }