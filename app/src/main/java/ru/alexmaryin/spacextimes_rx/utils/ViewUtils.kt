package ru.alexmaryin.spacextimes_rx.utils

import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.drawToBitmap
import okhttp3.internal.notify
import ru.alexmaryin.spacextimes_rx.R

fun View.swapVisibility() {
    visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
}

infix fun TextView.expandOrSwapTo(lines: Int) {
    maxLines = if (maxLines == lines) Integer.MAX_VALUE else lines
}

@Suppress("DEPRECATION")
fun Context.getCurrentLocale(): String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
    resources.configuration.locales[0].language else resources.configuration.locale.language

fun View.saveToStorage(context: Context, filename: String) {
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
    resolver.openOutputStream(imageUri!!)?.let { drawToBitmap().compress(Bitmap.CompressFormat.JPEG, 100, it) }
    val pendingIntent = PendingIntent.getActivity(context, 0,Intent(Intent.ACTION_VIEW, imageUri).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }, 0)
    val notification = NotificationCompat.Builder(context, "spacex")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("Загрузка изображения")
        .setContentText("Фото сохранено в галерею.")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
    with(NotificationManagerCompat.from(context)) {
        notify(1, notification.build())
    }
}