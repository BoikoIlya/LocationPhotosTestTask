package com.exmaple.locationphotostesttask.photos.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.exmaple.locationphotostesttask.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Ilya Boiko @camancho
on 14.10.2023.
 **/
@AndroidEntryPoint
class EnableGpsAndRetryFragment: DialogFragment() {

 private val viewModel: PhotosViewModel by activityViewModels()

 override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

   val listener = DialogInterface.OnClickListener { _, buttonId ->
    if (buttonId == DialogInterface.BUTTON_POSITIVE) {
     val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
      arguments?.getParcelable(ARG_KEY, Uri::class.java)
     else
      arguments?.getParcelable(ARG_KEY)

     viewModel.postPhoto(true,uri)
    }
   }
   val dialog = MaterialAlertDialogBuilder(requireContext())
    .setMessage(R.string.enable_gps_and_network_post_again)
    .setPositiveButton(R.string.post_photo_again, listener)
    .setNegativeButton(R.string.close, listener)
    .create()

  dialog.setCanceledOnTouchOutside(false)

  return dialog
 }



 companion object {
  private const val ARG_KEY = "argument_key"

  fun newInstance(yourArgument: Uri): EnableGpsAndRetryFragment {
   val fragment = EnableGpsAndRetryFragment()
   val args = Bundle()
   args.putParcelable(ARG_KEY, yourArgument)
   fragment.arguments = args
   return fragment
  }
 }

}