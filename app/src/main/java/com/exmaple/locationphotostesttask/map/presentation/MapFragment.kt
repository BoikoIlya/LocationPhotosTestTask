package com.exmaple.locationphotostesttask.map.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.exmaple.locationphotostesttask.R
import com.exmaple.locationphotostesttask.databinding.PhotosFragmentBinding
import com.exmaple.locationphotostesttask.photos.presentation.PhotoUi
import com.exmaple.locationphotostesttask.photos.presentation.PhotosViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Ilya Boiko @camancho
on 13.10.2023.
 **/
@AndroidEntryPoint
class MapFragment: Fragment(R.layout.map_fragment),OnMapReadyCallback {

    private val viewModel: PhotosViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(map: GoogleMap) {
        val showOnMapMapper = PhotoUi.ShowOnMap(map)
        val moveAndZoomPointMapper = PhotoUi.MoveToMarkerAndZoom(map)

        lifecycleScope.launch {
            viewModel.collectPhotosListCommunication(this@MapFragment){list->
                list.map { it.map(showOnMapMapper) }
                list.last().map(moveAndZoomPointMapper)
            }
        }
    }

}