@file:Suppress("unused", "LocalVariableName")

package me.reezy.cosmo.utility

import android.content.*
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.io.OutputStream



/**
 * 保存图片到相册(Pictures 文件夹)
 */
fun File.saveToAlbum(context: Context, filename: String, relativePath: String? = null): Uri? {
    if (!canRead() || !exists()) {
        return null
    }
    return context.saveToAlbum(filename, relativePath) { output ->
        inputStream().use { input ->
            input.copyTo(output)
        }
    }
}

/**
 * 保存图片到相册(Pictures 文件夹)
 */
fun InputStream.saveToAlbum(context: Context, filename: String, relativePath: String? = null): Uri? {
    return context.saveToAlbum(filename, relativePath) { output ->
        use { input ->
            input.copyTo(output)
        }
    }
}

/**
 * 保存图片到相册(Pictures 文件夹)
 */
fun Bitmap.saveToAlbum(context: Context, filename: String, relativePath: String? = null, quality: Int = 75): Uri? {
    return context.saveToAlbum(filename, relativePath) {
        compress(filename.getBitmapFormat(), quality, it)
    }
}

private fun Context.saveToAlbum(filename: String, relativePath: String? = null, saveTo: (output: OutputStream) -> Unit): Uri? {

    val resolver = contentResolver
    val uri = resolver.insert(filename, relativePath)

    log("insert uri : $uri")

    if (uri == null) {
        return null
    }

    val output = try {
        resolver.openOutputStream(uri)
    } catch (e: FileNotFoundException) {
        log("open stream fail: $e")
        null
    } ?: return null

    output.use {
        saveTo(it)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(uri, values, null, null)
        } else {
            // 通知媒体库更新
            MediaScannerConnection.scanFile(this, arrayOf(uri.toString()), null, null)
        }

    }
    return uri
}


/**
 * 插入图片到媒体库
 */
private fun ContentResolver.insert(filename: String, relativePath: String?): Uri? {

    val values = ContentValues().apply {
        val mimeType = filename.getMimeType()
        if (mimeType != null) {
            put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        }
        val date = System.currentTimeMillis() / 1000
        put(MediaStore.Images.Media.DATE_ADDED, date)
        put(MediaStore.Images.Media.DATE_MODIFIED, date)
    }

    val albumDir = Environment.DIRECTORY_PICTURES
    
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val path = if (relativePath != null) "$albumDir/${relativePath}" else albumDir
        values.apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
            put(MediaStore.Images.Media.RELATIVE_PATH, path)
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }
        // 高版本直接插入(重复文件会自动重命名)
        return insert(MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY), values)
    } else {
        // 低版本手动处理重复文件名(在后拼接数字)
        val pic = Environment.getExternalStoragePublicDirectory(albumDir)
        val dir = if (relativePath != null) File(pic, relativePath) else pic

        if (!dir.exists() && !dir.mkdirs()) {
            log("insert fail: can not create Pictures directory")
            return null
        }

        val file = getInsertFile(dir, filename)

        log("insert file: ${file.absolutePath}")

        values.apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, file.name)
            put(MediaStore.Images.Media.DATA, file.absolutePath)
        }

        return insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }
}

private fun ContentResolver.getInsertFile(dir: File, filename: String): File {

    var index = 1
    var file = File(dir, filename)
    val name = file.nameWithoutExtension
    val extension = file.extension

    do {
        if (queryUri(file) == null) {
            return file
        }
        file = File(file.parent, "$name(${index++}).$extension")
    } while (true)
}

private fun ContentResolver.queryUri(file: File): Uri? {
    if (file.canRead() && file.exists()) {
        return Uri.fromFile(file)
    }

    val collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    val _id = MediaStore.Images.Media._ID
    val _data = MediaStore.Images.Media.DATA

    query(collection, arrayOf(_id, _data), "$_data == ?", arrayOf(file.absolutePath), null)?.use {
        while (it.moveToNext()) {
            val id = it.getLong(it.getColumnIndexOrThrow(_id))
            return ContentUris.withAppendedId(collection, id)
        }
    }
    return null
}


private fun String.getBitmapFormat(): Bitmap.CompressFormat {
    val filename = lowercase()
    return when {
        filename.endsWith(".png") -> Bitmap.CompressFormat.PNG
        filename.endsWith(".jpg") || filename.endsWith(".jpeg") -> Bitmap.CompressFormat.JPEG
        filename.endsWith(".webp") -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            Bitmap.CompressFormat.WEBP_LOSSLESS else Bitmap.CompressFormat.WEBP
        else -> Bitmap.CompressFormat.PNG
    }
}

private fun String.getMimeType(): String? {
    val filename = lowercase()
    return when {
        filename.endsWith(".png") -> "image/png"
        filename.endsWith(".jpg") || filename.endsWith(".jpeg") -> "image/jpeg"
        filename.endsWith(".webp") -> "image/webp"
        filename.endsWith(".gif") -> "image/gif"
        else -> null
    }
}

private fun log(message: String) {
    Log.e("OoO.album", message)
}