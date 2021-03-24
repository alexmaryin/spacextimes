package ru.alexmaryin.spacextimes_rx.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.drawToBitmap
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

fun prepareNotificationsChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel(
            context.getString(R.string.notifications_id),
            context.getString(R.string.notifications_channel),
            NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = context.getString(R.string.notifications_description)
        }.apply { NotificationManagerCompat.from(context).createNotificationChannel(this) }
    }
}

fun notifyOnSavedPhoto(context: Context, imageUri: Uri) {
    val pendingIntent = PendingIntent.getActivity(context, 0, Intent(Intent.ACTION_VIEW, imageUri).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }, 0)
    val notification = NotificationCompat.Builder(context, context.getString(R.string.notifications_id))
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("Изображение сохранено")
        .setContentText("Нажмите, чтобы открыть в галерее или редакторе.")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
    with(NotificationManagerCompat.from(context)) {
        notify(1, notification.build())
    }
}
