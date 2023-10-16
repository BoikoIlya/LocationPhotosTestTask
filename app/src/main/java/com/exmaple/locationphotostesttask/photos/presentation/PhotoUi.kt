package com.exmaple.locationphotostesttask.photos.presentation

import android.util.Log
import com.exmaple.locationphotostesttask.core.ImageLoader
import com.exmaple.locationphotostesttask.databinding.PhotoItemBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Created by Ilya Boiko @camancho
on 13.10.2023.
 **/
data class PhotoUi(
 private val id: Int,
 private val photoUrl: String,
 private val date: String,
 private val time: String,
 private val lat: Double,
 private val lng: Double,
){

 fun map(item: PhotoUi): Boolean = item.id== this.id

 fun <T>map (mapper: Mapper<T>): T = mapper.map(id, photoUrl, date, time, lat, lng)


 interface Mapper<T> {
    fun map(
     id: Int,
     photoUrl: String,
     date: String,
     time: String,
     lat: Double,
     lng: Double,
    ):T
 }


 class ShowInRecycler(
  private val binding: PhotoItemBinding,
  private val imageLoader: ImageLoader,
  private val onLongClick:(Int)->Unit,
 ): Mapper<Unit> {

  override fun map(
   id: Int,
   photoUrl: String,
   date: String,
   time: String,
   lat: Double,
   lng: Double,
  ) = with(binding) {
    imageLoader.loadImage(imageView,photoUrl)
    dataTv.text = date
    root.setOnLongClickListener {
     onLongClick.invoke(id)
    true
   }
  }
 }

 class ShowOnMap(
  private val map: GoogleMap,
 ): Mapper<Unit> {

  override fun map(
   id: Int,
   photoUrl: String,
   date: String,
   time: String,
   lat: Double,
   lng: Double,
  ) {
    val point = LatLng(lat,lng)
    val marker = map.addMarker(
     MarkerOptions().position(point).title(String.format("$date $time")).draggable(true))

    map.setOnMarkerClickListener {
      if(it.id != marker!!.id) {
       it.showInfoWindow()

      }

     return@setOnMarkerClickListener true
    }

  }
 }

 class MoveToMarkerAndZoom(
  private val map: GoogleMap,
 ): Mapper<Unit> {

  companion object{
   private const val zoomValue: Float = 20f
  }

  override fun map(
   id: Int,
   photoUrl: String,
   date: String,
   time: String,
   lat: Double,
   lng: Double,
  ) {
   val point = LatLng(lat,lng)
   map.moveCamera(CameraUpdateFactory.newLatLngZoom(point,zoomValue))
  }
 }
}
