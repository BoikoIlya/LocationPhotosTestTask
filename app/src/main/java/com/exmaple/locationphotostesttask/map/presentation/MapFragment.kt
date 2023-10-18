package com.exmaple.locationphotostesttask.map.presentation

import android.content.ContentValues
import android.content.pm.PackageManager
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.exmaple.locationphotostesttask.R
import com.exmaple.locationphotostesttask.databinding.MapFragmentBinding
import com.exmaple.locationphotostesttask.photos.presentation.BaseTakePhotoFragment
import com.exmaple.locationphotostesttask.photos.presentation.PhotoUi
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Ilya Boiko @camancho
on 13.10.2023.
 **/
@AndroidEntryPoint
class MapFragment: BaseTakePhotoFragment(R.layout.map_fragment),OnMapReadyCallback {

    private val binding by viewBinding(MapFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun getOpenCameraFab(): FloatingActionButton = binding.openCameraFab

    override fun onMapReady(map: GoogleMap) {
        val showOnMapMapper = PhotoUi.ShowOnMap(map)
        val moveAndZoomPointMapper = PhotoUi.MoveToMarkerAndZoom(map)

        lifecycleScope.launch {
            viewModel.collectDataListCommunication(this@MapFragment){list->
                list.map { it.map(showOnMapMapper) }
                if(list.isNotEmpty()) list.first().map(moveAndZoomPointMapper)
            }
        }
    }

}