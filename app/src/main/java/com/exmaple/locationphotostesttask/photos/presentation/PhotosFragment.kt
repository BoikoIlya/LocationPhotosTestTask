package com.exmaple.locationphotostesttask.photos.presentation

import android.content.ContentValues
import android.content.pm.PackageManager
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.exmaple.locationphotostesttask.R
import com.exmaple.locationphotostesttask.core.ImageLoader
import com.exmaple.locationphotostesttask.core.PagingListener
import com.exmaple.locationphotostesttask.core.PagingSource
import com.exmaple.locationphotostesttask.databinding.PhotosFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Ilya Boiko @camancho
on 13.10.2023.
 **/
@AndroidEntryPoint
class PhotosFragment: Fragment(R.layout.photos_fragment) {

    private val binding by viewBinding(PhotosFragmentBinding::bind)


    @Inject
    lateinit var imageLoader: ImageLoader

    private val viewModel: PhotosViewModel by activityViewModels()

    private var photoUri: Uri? = null

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture(),
    ){result->
        viewModel.postPhoto(result, photoUri)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photosAdapter = PhotosAdapter(imageLoader,{

        },{id-> viewModel.launchDeletePhotoDialog(id) })

        val loadStateAdapter = LoadStateAdapter{
            viewModel.loadNewPage()
        }

        val concatAdapter = ConcatAdapter(photosAdapter,loadStateAdapter)

        val layoutManager = GridLayoutManager(requireContext(),3)
        binding.photosRecycler.layoutManager = layoutManager
        binding.photosRecycler.adapter = concatAdapter

        val pagingLister = PagingListener(PagingSource.PhotosPagingSource.pageSize,0){
            viewModel.loadNewPage()
        }
        binding.photosRecycler.addOnScrollListener(pagingLister)

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int =
                if(position == concatAdapter.itemCount-1 && loadStateAdapter.itemCount>0) 3
                 else 1
            }

        lifecycleScope.launch {
            viewModel.collectPhotosListCommunication(this@PhotosFragment){
                val state = layoutManager.onSaveInstanceState()
                photosAdapter.setList(it)
                layoutManager.onRestoreInstanceState(state)
            }
        }

        lifecycleScope.launch {
            viewModel.collectPhotosStateCommunication(this@PhotosFragment){
                it.apply(loadStateAdapter,pagingLister)
            }
        }

        binding.openCameraFab.setOnClickListener {
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
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_CODE)
            }
        }
    }

    companion object{
         const val REQUEST_CODE = 1
    }

}