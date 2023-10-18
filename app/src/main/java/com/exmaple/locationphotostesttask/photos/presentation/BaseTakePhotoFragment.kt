package com.exmaple.locationphotostesttask.photos.presentation

import android.content.ContentValues
import android.content.pm.PackageManager
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Created by Ilya Boiko @camancho
on 17.10.2023.
 **/
abstract class BaseTakePhotoFragment(layoutId: Int): Fragment(layoutId) {

   protected val viewModel: PhotosViewModel by activityViewModels()

   private var photoUri: Uri? = null

   private val resultLauncher = registerForActivityResult(
    ActivityResultContracts.TakePicture(),
   ){result->
    viewModel.postPhoto(result, photoUri)
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    getOpenCameraFab().setOnClickListener {
     if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
      == PackageManager.PERMISSION_GRANTED){

      viewModel.checkConnectionAndGps {

       val values = ContentValues()
       values.put(MediaStore.Images.Media.TITLE, System.currentTimeMillis())
       values.put(
        MediaStore.Images.Media.ORIENTATION,
        ExifInterface.ORIENTATION_UNDEFINED
       )
       photoUri = requireContext().contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        values
       )

       resultLauncher.launch(photoUri)

      }
     }else{
      ActivityCompat.requestPermissions(requireActivity(),
       arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
       PhotosFragment.request_code
      )
     }
    }
   }

   protected abstract fun getOpenCameraFab(): FloatingActionButton
}