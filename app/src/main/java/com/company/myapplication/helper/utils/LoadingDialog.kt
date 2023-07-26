package com.company.myapplication.helper.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.company.myapplication.R
import android.os.Build
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi

class LoadingDialog(context: Context) : Dialog(context) {

    init {
        setContentView(R.layout.dialog_loading)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setCancelable(false)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun show() {
        super.show()
        setFullScreen()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun setFullScreen() {
        val windowInsetsController = window?.decorView?.windowInsetsController

        // Hide both the status bar and the navigation bar.
        windowInsetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())

        // Make the content appear under the status bar and navigation bar.
        windowInsetsController?.systemBarsBehavior =
            WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        // Enable immersive mode, so that even if the user swipes, the bars stay hidden.
        windowInsetsController?.systemBarsBehavior =
            WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun dismiss() {
        super.dismiss()
        exitFullScreen()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun exitFullScreen() {
        val windowInsetsController = window?.decorView?.windowInsetsController

        // Show both the status bar and the navigation bar.
        windowInsetsController?.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())

        // Disable immersive mode, so that the bars can auto-hide again.
        windowInsetsController?.systemBarsBehavior =
            WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

}