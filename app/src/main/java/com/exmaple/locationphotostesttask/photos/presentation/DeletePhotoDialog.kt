package com.exmaple.locationphotostesttask.photos.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.exmaple.locationphotostesttask.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Ilya Boiko @camancho
on 15.10.2023.
 **/
@AndroidEntryPoint
class DeletePhotoDialog: DialogFragment() {

 private val viewModel: PhotosViewModel by activityViewModels()

 override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

  val listener = DialogInterface.OnClickListener { _, buttonId ->
   if (buttonId == DialogInterface.BUTTON_POSITIVE)
    viewModel.deletePhoto(requireArguments().getInt(ARG_KEY))
  }

  return MaterialAlertDialogBuilder(requireContext())
   .setMessage(R.string.delete_photo_dialog)
   .setPositiveButton(R.string.yes, listener)
   .setNegativeButton(R.string.no, listener)
   .create()
 }


 companion object {
  private const val ARG_KEY = "argument_key"

  fun newInstance(id: Int): DeletePhotoDialog {
   val fragment = DeletePhotoDialog()
   val args = Bundle()
   args.putInt(ARG_KEY, id)
   fragment.arguments = args
   return fragment
  }

 }
}
