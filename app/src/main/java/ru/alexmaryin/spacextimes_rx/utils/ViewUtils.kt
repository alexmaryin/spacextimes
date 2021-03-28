package ru.alexmaryin.spacextimes_rx.utils

import android.app.*
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.drawToBitmap
import ru.alexmaryin.spacextimes_rx.R

fun View.swapVisibility() {
    visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
}

infix fun TextView.expandOrSwapTo(lines: Int) {
    maxLines = if (maxLines == lines) Integer.MAX_VALUE else lines
}

fun View.saveToStorage(context: Context, filename: String): Uri? {
    var res = false
    val resolver = context.contentResolver
    val images = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
    } else MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    val photo = ContentValues().apply {
        put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis())
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.DISPLAY_NAME, filename)
    }
    val imageUri = resolver.insert(images, photo)
    resolver.openOutputStream(imageUri!!)?.let { res = drawToBitmap().compress(Bitmap.CompressFormat.JPEG, 100, it) }
    return if (res) imageUri else null
}

fun downloadByLongClickListener(url: String?, filename: String) = View.OnLongClickListener { view ->
    view?.let {
        AlertDialog.Builder(it.context)
            .setTitle(it.context.getString(R.string.saving_title_string))
            .setMessage(it.context.getString(R.string.save_image_dialog_string))
            .setPositiveButton(it.context.getString(R.string.agreeText)) { dialog, _ ->
                val request = DownloadManager.Request(Uri.parse(url)).apply {
                    setTitle(it.context.getString(R.string.saving_title_string))
                    setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, filename)
                    setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                }
                (it.context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).apply { enqueue(request) }
                dialog.dismiss()
            }
            .setNegativeButton(it.context.getString(R.string.cancelText)) { dialog, _ -> dialog.dismiss() }
            .show()
        return@OnLongClickListener true
    }
    false
}

fun saveByLongClickListener(context: Context, filename: String) = View.OnLongClickListener { view ->
    AlertDialog.Builder(context)
        .setTitle(context.getString(R.string.saving_title_string))
        .setMessage(context.getString(R.string.save_image_dialog_string))
        .setPositiveButton(context.getString(R.string.agreeText)) { dialog, _ ->
            view.saveToStorage(context, filename)?.let {
                context.notifyOnSavedPhoto(it)
            } ?: Toast.makeText(context, context.getString(R.string.failed_image_save_string), Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        .setNegativeButton(context.getString(R.string.cancelText)) { dialog, _ -> dialog.dismiss() }
        .show()
    true
}
