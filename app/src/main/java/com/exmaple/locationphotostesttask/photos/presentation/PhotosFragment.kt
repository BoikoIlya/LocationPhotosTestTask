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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.exmaple.locationphotostesttask.R
import com.exmaple.locationphotostesttask.core.ImageLoader
import com.exmaple.locationphotostesttask.core.PagingListener
import com.exmaple.locationphotostesttask.core.PagingSource
import com.exmaple.locationphotostesttask.databinding.PhotosFragmentBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Ilya Boiko @camancho
on 13.10.2023.
 **/
@AndroidEntryPoint
class PhotosFragment: BaseTakePhotoFragment(R.layout.photos_fragment) {

    private val binding by viewBinding(PhotosFragmentBinding::bind)

    @Inject
    lateinit var imageLoader: ImageLoader


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photosAdapter = PhotosAdapter(imageLoader,{id->
            val bundle = Bundle()
            bundle.putInt(photo_id_key,id)
            findNavController().navigate(R.id.action_photosFragment_to_photoDetails,bundle)
        },{id-> viewModel.launchDeletePhotoDialog(id) })

        val loadStateAdapter = LoadStateAdapter{
            viewModel.loadNewPage()
        }

        val concatAdapter = ConcatAdapter(photosAdapter,loadStateAdapter)

        val layoutManager = GridLayoutManager(requireContext(),3)
        binding.photosRecycler.layoutManager = layoutManager
        binding.photosRecycler.adapter = concatAdapter

        val pagingLister = PagingListener.Base(PagingSource.PhotosPagingSource.pageSize,0){
            viewModel.loadNewPage()
        }
        binding.photosRecycler.addOnScrollListener(pagingLister)

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int =
                if(position == concatAdapter.itemCount-1 && loadStateAdapter.itemCount>0) 3
                 else 1
            }

        lifecycleScope.launch {
            viewModel.collectDataListCommunication(this@PhotosFragment){
                val state = layoutManager.onSaveInstanceState()
                photosAdapter.setList(it)
                layoutManager.onRestoreInstanceState(state)
            }
        }

        lifecycleScope.launch {
            viewModel.collectLoadStateCommunication(this@PhotosFragment){
                it.apply(loadStateAdapter,pagingLister)
            }
        }
    }

    override fun getOpenCameraFab(): FloatingActionButton = binding.openCameraFab

    companion object{
         const val request_code = 1
        const val photo_id_key: String = "photoId"
    }

}