package com.exmaple.locationphotostesttask.photodetails.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.exmaple.locationphotostesttask.databinding.CommentItemBinding

/**
 * Created by Ilya Boiko @camancho
on 17.10.2023.
 **/
class CommentsAdapter(
    private val onLongClick:(Int)->Unit
): RecyclerView.Adapter<CommentsViewHolder>() {

    private val commentsList = emptyList<CommentUi>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        return CommentsViewHolder(
            CommentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),onLongClick
        )
    }

    override fun getItemCount(): Int = commentsList.size

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.bind(commentsList[position])
    }

    fun setList(newCommentsList: List<CommentUi>){
        val callback = CommentsDiffUtilCallback(newCommentsList,commentsList)
        val diff = DiffUtil.calculateDiff(callback)
        commentsList.clear()
        commentsList.addAll(newCommentsList)
        diff.dispatchUpdatesTo(this)
    }
}

class CommentsViewHolder(
    private val binding: CommentItemBinding,
    private val onLongClick:(Int)->Unit
): ViewHolder(binding.root){

    fun bind(item: CommentUi) = item.showToUi(binding,onLongClick)

}

class CommentsDiffUtilCallback(
    private val newList: List<CommentUi>,
    private val oldList: List<CommentUi>
): DiffUtil.Callback(){

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].map(newList[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}