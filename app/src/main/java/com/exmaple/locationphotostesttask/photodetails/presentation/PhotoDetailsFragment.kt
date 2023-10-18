package com.exmaple.locationphotostesttask.photodetails.presentation

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.exmaple.locationphotostesttask.R
import com.exmaple.locationphotostesttask.core.ImageLoader
import com.exmaple.locationphotostesttask.core.PagingListener
import com.exmaple.locationphotostesttask.core.PagingSource
import com.exmaple.locationphotostesttask.databinding.PhotoDetailsFragmentBinding
import com.exmaple.locationphotostesttask.main.presentation.MainActivity
import com.exmaple.locationphotostesttask.photos.presentation.LoadStateAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 16.10.2023.
 **/
@AndroidEntryPoint
class PhotoDetailsFragment : Fragment(R.layout.photo_details_fragment) {

    private val args: PhotoDetailsFragmentArgs by navArgs()

    private val binding by viewBinding(PhotoDetailsFragmentBinding::bind)

    private val viewModel: PhotoDetailsViewModel by viewModels()

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        override fun afterTextChanged(s: Editable?) {
            viewModel.commentSectionValidation(s.toString())
        }

    }

    @Inject
    lateinit var imageLoader: ImageLoader

    private var deletingCommentId: Int = 0
    private val listener = DialogInterface.OnClickListener { _, buttonId ->
        if (buttonId == DialogInterface.BUTTON_POSITIVE)
            viewModel.deleteComment(args.photoId,deletingCommentId)
    }

    private lateinit var deleteDialog: AlertDialog


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deleteDialog = MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.delete_comment_dialog)
            .setPositiveButton(R.string.yes, listener)
            .setNegativeButton(R.string.no, listener)
            .create()

        viewModel.fetchPhotoDetails(args.photoId)
        viewModel.fetchComments(args.photoId)

        val commentsAdapter = CommentsAdapter { commentId ->
            deletingCommentId = commentId
            deleteDialog.show()
           // viewModel.launchDeleteCommentDialog(photoId, commentId)
        }

        val loadStateAdapter = LoadStateAdapter {
            viewModel.fetchComments(args.photoId)
        }

        val concatAdapter = ConcatAdapter(loadStateAdapter, commentsAdapter)

        val layoutManager = LinearLayoutManager(requireContext()).apply {
            stackFromEnd = true
            reverseLayout = false
        }

        binding.commentsRecycler.layoutManager = layoutManager
        binding.commentsRecycler.adapter = concatAdapter

        val pagingListener =
            PagingListener.PagingListenerComments(PagingSource.CommentsPagingSource.pageSize, 0) {
                viewModel.fetchComments(args.photoId)
            }

        binding.commentsRecycler.addOnScrollListener(pagingListener)

        lifecycleScope.launch {
            viewModel.collectLoadStateCommunication(this@PhotoDetailsFragment) {
                it.apply(loadStateAdapter, pagingListener)
            }
        }

        lifecycleScope.launch {
            viewModel.collectPhotoDetailsCommunication(this@PhotoDetailsFragment) {
                it.showToUi(binding.PhotoImgView, imageLoader, binding.dataTimeTextView)
            }
        }

        lifecycleScope.launch {
            viewModel.collectDataListCommunication(this@PhotoDetailsFragment) {
                Log.d("tag", "onViewCreated: ${it.size}")
                commentsAdapter.setList(it)
            }
        }

        lifecycleScope.launch {
            viewModel.collectCommentSectionStateCommunication(this@PhotoDetailsFragment) {
                it.apply(binding)
            }
        }

        binding.sendBtn.setOnClickListener {
            viewModel.postComment(binding.commentEdText.text.toString(), args.photoId)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.commentEdText.addTextChangedListener(textWatcher)
    }

    override fun onPause() {
        super.onPause()
        binding.commentEdText.removeTextChangedListener(textWatcher)
    }

}