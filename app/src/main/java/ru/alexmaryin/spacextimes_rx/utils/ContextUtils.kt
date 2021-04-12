package ru.alexmaryin.spacextimes_rx.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.TypedValue
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.alexmaryin.spacextimes_rx.R
import java.util.*

fun Context.getColorIdFromAttr(attrId: Int): Int = TypedValue().apply {
    theme.resolveAttribute(attrId, this, true)
}.resourceId

fun Context.prepareNotificationsChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel(
            getString(R.string.notifications_id),
            getString(R.string.notifications_channel),
            NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = getString(R.string.notifications_description)
        }.apply { NotificationManagerCompat.from(this@prepareNotificationsChannel).createNotificationChannel(this) }
    }
}

@SuppressLint("UnspecifiedImmutableFlag")
fun Context.notifyOnSavedPhoto(imageUri: Uri) {
    val pendingIntent = PendingIntent.getActivity(this, 0, Intent(Intent.ACTION_VIEW, imageUri).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }, 0)
    val notification = NotificationCompat.Builder(this, getString(R.string.notifications_id))
        .setSmallIcon(R.mipmap.logo01_beta)
        .setContentTitle(getString(R.string.saved_image_notification))
        .setContentText(getString(R.string.saved_image_text_notification))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
    with(NotificationManagerCompat.from(this)) {
        notify(1, notification.build())
    }
}

@Suppress("DEPRECATION")
fun Context.currentLocaleLang(): String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
    resources.configuration.locales[0].language else resources.configuration.locale.language

@Suppress("DEPRECATION")
fun Context.currentLocale(): Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
    resources.configuration.locales[0] else resources.configuration.locale