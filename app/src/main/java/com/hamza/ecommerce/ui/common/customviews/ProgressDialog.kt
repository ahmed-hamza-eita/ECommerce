package com.hamza.ecommerce.ui.common.customviews

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.hamza.ecommerce.R


class ProgressDialog {
    companion object {
        @SuppressLint("InflateParams")
        fun createProgressDialog(context: Context): Dialog {
            val dialog = Dialog(context)
            val inflate =
                LayoutInflater.from(context).inflate(R.layout.progress_dialog_layout, null)
            dialog.setContentView(inflate)
            dialog.setCancelable(false)
            dialog.window?.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT)
            )
            return dialog
        }
    }
}