package com.company.myapplication.helper.extensions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.company.myapplication.R
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.company.myapplication.helper.utils.console

fun Context.showErrorSnackBar(rootView: View, msg: String) {
    val sb: Snackbar = Snackbar.make(rootView, msg, Snackbar.LENGTH_SHORT)
    val sbView = sb.view
    sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.Red))
    sb.show()
}

fun Context.showSuccessSnackBar(rootView: View, msg: String) {
    val sb: Snackbar = Snackbar.make(rootView, msg, Snackbar.LENGTH_SHORT)
    val sbView = sb.view
    sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
    sb.show()
}

fun View.gone() {
    if (visibility != View.GONE) {
        visibility = View.GONE

    }
}

fun View.visible() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun View.invisible() {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}


fun Activity.checkCameraPermission(): Boolean {
    val result = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.CAMERA
    )
    return result == PackageManager.PERMISSION_GRANTED
}

fun Activity.requestUserPermission() {
    Dexter.withContext(this)
        .withPermissions(
            Manifest.permission.INTERNET,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                token?.continuePermissionRequest()
            }

            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                var isAnyFalse = false
                report.deniedPermissionResponses.forEach { p ->

                    if (p.permissionName != "android.permission.MANAGE_EXTERNAL_STORAGE") {
                        console("permissionName = ${p.permissionName}")
                        isAnyFalse = true
                        return@forEach
                    }
                }
                if (isAnyFalse) {
                    console("onPermissionsChecked")
                }
            }

        }).withErrorListener { error ->
            Toast.makeText(
                this,
                "Error occurred! $error",
                Toast.LENGTH_SHORT
            ).show()
        }
        .onSameThread()
        .check()
}
