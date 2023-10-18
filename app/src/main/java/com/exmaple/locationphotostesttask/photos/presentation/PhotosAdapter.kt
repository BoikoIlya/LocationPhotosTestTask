package com.exmaple.locationphotostesttask.photos.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.exmaple.locationphotostesttask.core.ImageLoader
import com.exmaple.locationphotostesttask.databinding.PhotoItemBinding

/**
 * Created by Ilya Boiko @camancho
on 13.10.2023.
 **/
class PhotosAdapter(
    private val imageLoader: ImageLoader,
    private val onClick:(Int)->Unit,
    private val onLongClick:(Int)->Unit,
): RecyclerView.Adapter<PhotosViewHolder>() {

    private val photos = emptyList<PhotoUi>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        return PhotosViewHolder(
            PhotoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),imageLoader, onClick,onLongClick
        )
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    fun setList(newList: List<PhotoUi>){
        val callback = PhotoUiDiffUtillCallback(photos,newList)
        val diff = DiffUtil.calculateDiff(callback)
        photos.clear()
        photos.addAll(newList)
        diff.dispatchUpdatesTo(this)
    }
}


class PhotosViewHolder(
    private val binding: PhotoItemBinding,
    private val imageLoader: ImageLoader,
    private val onClick:(Int)->Unit,
    private val onLongClick:(Int)->Unit,
): ViewHolder(binding.root){

    val mapper = PhotoUi.ShowInRecycler(binding, imageLoader,onLongClick,onClick)

    fun bind(item: PhotoUi) {
        item.map(mapper)
    }
}

class PhotoUiDiffUtillCallback(
    private val oldList: List<PhotoUi>,
    private val newList: List<PhotoUi>,
): DiffUtil.Callback(){

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
        = oldList[oldItemPosition].map(newList[newItemPosition])


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
        = oldList[oldItemPosition] == newList[newItemPosition]

}