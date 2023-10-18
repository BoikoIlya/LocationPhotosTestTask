package com.exmaple.locationphotostesttask.main.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.DialogFragment
import com.exmaple.locationphotostesttask.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs


/**
 * Created by Ilya Boiko @camancho
on 14.10.2023.
 **/
class LocationPermissionDialog: DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val listener = DialogInterface.OnClickListener { _, buttonId ->
            if (buttonId == DialogInterface.BUTTON_POSITIVE)
            {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val packageName = requireContext().packageName
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                requireContext().startActivity(intent)
            }
        }
        return MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.location_perm_dialog)
            .setPositiveButton(R.string.open_settings, listener)
            .setNegativeButton(R.string.close, listener)
            .create()
    }
}