package me.reezy.cosmo.utility.extension

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File

fun Intent.setGrantFile(context: Context, type: String, file: File, authority: String = "${context.packageName}.fileprovider", writeEnable: Boolean = false): Intent {
    if (Build.VERSION.SDK_INT >= 24) {
        setDataAndType(FileProvider.getUriForFile(context, authority, file), type)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        if (writeEnable) {
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
    } else {
        setDataAndType(Uri.fromFile(file), type)
    }
    return this
}