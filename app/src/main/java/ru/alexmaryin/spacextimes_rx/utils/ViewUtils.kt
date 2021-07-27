package ru.alexmaryin.spacextimes_rx.utils

import android.Manifest
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import com.synnapps.carouselview.ImageClickListener
import ru.alexmaryin.spacextimes_rx.R

fun View.swapVisibility() {
    visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
}

infix fun TextView.expandOrSwapTo(lines: Int) {
    maxLines = if (maxLines == lines) Integer.MAX_VALUE else lines
}

fun getUriForPhoto(context: Context, filename: String): Uri? = run {
    val resolver = context.contentResolver
    val images = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
    } else MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    val photo = ContentValues().apply {
        put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis())
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.DISPLAY_NAME, filename)
    }
    resolver.insert(images, photo)
}

fun View.saveToStorage(context: Context, filename: String): Uri? {
    val imageUri = getUriForPhoto(context, filename)
    return imageUri?.apply {
        val stream = context.contentResolver.openOutputStream(this)
        drawToBitmap().compress(Bitmap.CompressFormat.JPEG, 80, stream)
    }
}

fun View.openLink(url: String?) {
    url?.let {
        Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(url)
        }.run { context.startActivity(this) }
    }
}

private fun downloadDialog(context: Context, url: String?, filename: String) =
    AlertDialog.Builder(context)
        .setTitle(context.getString(R.string.saving_title_string))
        .setMessage(context.getString(R.string.save_image_dialog_string))
        .setPositiveButton(context.getString(R.string.agreeText)) { dialog, _ ->
            val request = DownloadManager.Request(Uri.parse(url)).apply {
                setTitle(context.getString(R.string.saving_title_string))
                setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI + DownloadManager.Request.NETWORK_MOBILE)
                setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename)
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            }
            (context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).apply { enqueue(request) }
            dialog.dismiss()
        }
        .setNegativeButton(context.getString(R.string.cancelText)) { dialog, _ -> dialog.dismiss() }

fun downloadByLongClickListener(url: String?, filename: String) = View.OnLongClickListener { view ->
    view?.let {
        try {
            downloadDialog(it.context, url, filename).show()
        } catch (e: Exception) {
            Log.e("DOWNLOAD", "Download error: ${e.message}\n${e.stackTrace}")
        }
        return@OnLongClickListener true
    }
    false
}

fun Fragment.saveByLongClickListener(filename: String) = View.OnLongClickListener { view ->

    val dialog = AlertDialog.Builder(requireContext())
        .setTitle(getString(R.string.saving_title_string))
        .setMessage(getString(R.string.save_image_dialog_string))
        .setPositiveButton(getString(R.string.agreeText)) { dialog, _ ->
            view.saveToStorage(requireContext(), filename)?.let {
                requireContext().notifyOnSavedPhoto(it)
            } ?: Toast.makeText(requireContext(), getString(R.string.failed_image_save_string), Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        .setNegativeButton(getString(R.string.cancelText)) { dialog, _ -> dialog.dismiss() }

     dialog.show()

    true
}

fun downloadImageFromCarousel(context: Context, images: List<String>, filename: String) = ImageClickListener { position ->
    try {
        downloadDialog(context, images[position], filename).show()
    } catch (e: Exception) {
        Log.e("DOWNLOAD", "Download error: ${e.message}\n${e.stackTrace}")
    }
}
