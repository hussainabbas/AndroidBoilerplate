package com.company.myapplication.helper.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.text.format.DateUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.myapplication.BuildConfig
import com.company.myapplication.presentation.activities.MainActivity
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStream
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset


//fun setUserValues(context: Context, userData: UserProfileResponse) {
//    val appPreferences = AppPreferences(context)
//    val gson = Gson()
//    val user = gson.toJson(userData)
//    appPreferences.putString(AppPreferences.USER, user)
//}
//
//fun getUserValues(context: Context): UserProfileResponse? {
//    val appPreferences = AppPreferences(context)
//    val parentValue = appPreferences.getString(AppPreferences.USER)
//    if (parentValue.isNotEmpty()) {
//        val gson = Gson()
//        return gson.fromJson(parentValue, UserProfileResponse::class.java)
//    }
//    return null
//}


fun hideKeyboard(activity: Activity) {
    val imm: InputMethodManager =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}


fun console(message: String) {
    if (BuildConfig.DEBUG) {
        Log.e("CONSOLE", message)
    }
}

@SuppressLint("HardwareIds")
fun getUniqueDeviceId(context: Context): String {
    return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}

fun changeDrawableColor(color: Int, textView: TextView) {
    for (drawable in textView.compoundDrawables) {
        if (drawable != null) {
            drawable.colorFilter =
                PorterDuffColorFilter(
                    ContextCompat.getColor(textView.context, color),
                    PorterDuff.Mode.SRC_IN
                )
        }
    }
}

@SuppressLint("SimpleDateFormat", "NewApi")
fun getFormatDate(transactedAt: String): String {
    val instant = Instant.parse(transactedAt)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
    val timestamp = localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
    val currentTime = System.currentTimeMillis()
    val timeDifference = currentTime - timestamp
    val timeAgo =
        DateUtils.getRelativeTimeSpanString(timestamp, currentTime, DateUtils.MINUTE_IN_MILLIS)
    return timeAgo.toString()
}

//Hidden Logout
fun logout(activity: Activity) {
    val appPreferences = AppPreferences(activity)
    val token = appPreferences.getString(AppPreferences.TOKEN)
    val fcmToken = appPreferences.getString(AppPreferences.FCM_TOKEN)
    appPreferences.clearPreferences()
    appPreferences.putString(AppPreferences.TOKEN, token)
    appPreferences.putString(AppPreferences.FCM_TOKEN, fcmToken)

    val intent = Intent(
        activity,
        MainActivity::class.java
    )
    intent.putExtra(Consts.IS_LOGGED_OUT, true)
    activity.startActivity(intent)
    activity.finish()
}

fun logoutWithAlert(activity: Activity) {
    AlertDialog.Builder(activity)
        .setTitle("Logout")
        .setMessage("Are you sure you want to logout?")
        .setPositiveButton("Yes") { dialog, _ ->
            val appPreferences = AppPreferences(activity)
            val fcmToken = appPreferences.getString(AppPreferences.FCM_TOKEN)
            appPreferences.clearPreferences()
            appPreferences.putString(AppPreferences.FCM_TOKEN, fcmToken)
            val intent = Intent(
                activity,
                MainActivity::class.java
            )
            intent.putExtra(Consts.IS_LOGGED_OUT, true)
            activity.startActivity(intent)
            activity.finish()
            dialog.dismiss()
        }
        .setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}

fun recyclerViewInit(
    context: Context,
    recyclerView: RecyclerView,
    scroll: Boolean? = false,
    isGrid: Boolean? = false,
    gridSpan: Int? = 4,
    isReverse: Boolean? = false
): RecyclerView {
    if (isGrid == false) {
        val manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        manager.stackFromEnd = isReverse ?: false
        recyclerView.layoutManager = manager
    } else {
        val manager = GridLayoutManager(context, gridSpan ?: 4)
        recyclerView.layoutManager = manager
    }

    recyclerView.itemAnimator = DefaultItemAnimator()
    recyclerView.isNestedScrollingEnabled = scroll ?: false

    return recyclerView
}


fun showErrorAlert(title: String, message: String, context: Context) {
    AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setNegativeButton("Ok") { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}

fun createImageUri(context: Context): Uri? {
    return saveImage(
        context = context,
        displayName = "Bachat_${System.currentTimeMillis()}.png"
    )
}

@Nullable
@Throws(IOException::class)
private fun saveImage(
    context: Context,
    displayName: String
): Uri? {
    val relativeLocation: String = Environment.DIRECTORY_DCIM

    val contentValues = ContentValues()
    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation)
    }

    val resolver = context.contentResolver
    var stream: OutputStream? = null
    var uri: Uri? = null

    try {
        val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        uri = resolver.insert(contentUri, contentValues)

        if (uri == null) {
            throw IOException("Failed to create new MediaStore record.")
        }

        stream = resolver.openOutputStream(uri)

        if (stream == null) {
            throw IOException("Failed to get output stream.")
        }

    } catch (e: IOException) {
        if (uri != null) {
            // Don't leave an orphan entry in the MediaStore
            resolver.delete(uri, null, null)
        }

        throw e
    } finally {
        stream?.close()
    }

    return uri
}


@SuppressLint("InlinedApi")
fun getImageUri(inImage: Bitmap, context: Context): Uri? {
    val filename = "Bachat_${System.currentTimeMillis()}.png"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Xonder")
        }

        val resolver = context.contentResolver
        var imageUri: Uri? = null
        var outputStream: OutputStream? = null

        try {
            val collection =
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            imageUri = resolver.insert(collection, contentValues)
            outputStream = imageUri?.let { resolver.openOutputStream(it) }

            if (outputStream != null) {
                inImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            // Handle the exception as needed
        } finally {
            outputStream?.close()
        }

        return imageUri
    } else {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val path: String =
            MediaStore.Images.Media.insertImage(
                context.contentResolver,
                inImage,
                filename,
                null
            )
        return Uri.parse(path)
    }
}

fun getRealPathFromURI(contentURI: Uri, activity: Activity): String? {
    val result: String?
    val cursor: Cursor? =
        activity.contentResolver.query(
            contentURI, null, null, null, null
        )
    if (cursor == null) { // Source is Dropbox or other similar local file path
        result = contentURI.path
    } else {
        cursor.moveToFirst()
        val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        result = cursor.getString(idx)
        cursor.close()
    }
    return result
}

