package com.exmaple.locationphotostesttask.photodetails.presentation

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.exmaple.locationphotostesttask.R
import com.exmaple.locationphotostesttask.databinding.PhotoDetailsFragmentBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * Created by Ilya Boiko @camancho
on 17.10.2023.
 **/
sealed interface CommentSectionState {

    fun apply(binding: PhotoDetailsFragmentBinding)

    object Loading : CommentSectionState {

        override fun apply(binding: PhotoDetailsFragmentBinding) = with(binding) {
            commentInputLayout.isEnabled = false
            sendBtn.visibility = View.GONE
            sendProgress.visibility = View.VISIBLE
        }
    }

    abstract class DisableLoading(
        private val btnTintColor: Int,
    ) : CommentSectionState {

        override fun apply(binding: PhotoDetailsFragmentBinding) = with(binding) {
            commentInputLayout.isEnabled = true
            sendBtn.imageTintList = ColorStateList.valueOf(btnTintColor)
            sendBtn.visibility = View.VISIBLE
            sendProgress.visibility = View.GONE
        }

        object ReadyToSend : DisableLoading(Color.BLACK) {

            override fun apply(binding: PhotoDetailsFragmentBinding) = with(binding) {
                super.apply(binding)
                sendBtn.isEnabled = true
            }
        }

        open class NotReadyToSend : DisableLoading(Color.GRAY) {

            override fun apply(binding: PhotoDetailsFragmentBinding) = with(binding) {
                super.apply(binding)
                sendBtn.isEnabled = false
            }

            object SuccessPosting : NotReadyToSend() {

                override fun apply(binding: PhotoDetailsFragmentBinding) = with(binding) {
                    super.apply(binding)
                    commentInputLayout.isErrorEnabled = false
                    commentInputLayout.error = ""
                    binding.commentEdText.setText("")
                    val layoutManager = binding.commentsRecycler.layoutManager!!
                    if (layoutManager.itemCount > layoutManager.childCount)
                        binding.commentsRecycler.smoothScrollToPosition(layoutManager.itemCount - 1)
                }

            }

        }

        data class Failure(
            private val message: String,
        ) : DisableLoading(Color.BLACK) {

            override fun apply(binding: PhotoDetailsFragmentBinding) = with(binding) {
                super.apply(binding)
                commentInputLayout.isErrorEnabled = true
                commentInputLayout.error = message
            }

        }
    }

}