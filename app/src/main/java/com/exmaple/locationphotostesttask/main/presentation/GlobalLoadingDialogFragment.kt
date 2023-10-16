package com.exmaple.locationphotostesttask.main.presentation

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.exmaple.locationphotostesttask.R
import com.exmaple.locationphotostesttask.photos.presentation.EnableGpsAndRetryFragment

/**
 * Created by Ilya Boiko @camancho
on 14.10.2023.
 **/
class GlobalLoadingDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val progressBar = ProgressBar(requireContext())
        val color = ContextCompat.getColor(requireContext(), R.color.green)
        val colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
        progressBar.indeterminateDrawable.colorFilter = colorFilter


        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(progressBar)
            .setMessage(R.string.posting)
            .create()

        alertDialog.setCancelable(false)
        alertDialog.setCanceledOnTouchOutside(false)
        return alertDialog
    }


}