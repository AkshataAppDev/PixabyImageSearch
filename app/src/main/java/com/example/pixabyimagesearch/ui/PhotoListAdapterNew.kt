
package com.example.pixabyimagesearch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.example.pixabyimagesearch.R
import com.example.pixabyimagesearch.databinding.PhotoItemBinding
import com.example.pixabyimagesearch.models.Photo
import com.example.pixabyimagesearch.repository.AppExecutors

class PhotoListAdapterNew(
    appExecutors: AppExecutors,
    private val photoClickCallback: ((Photo) -> Unit)?
) : DataBoundListAdapter<Photo,PhotoItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.userId == newItem.userId
        }
    }
) {

    override fun createBinding(parent: ViewGroup): PhotoItemBinding {
        val binding = DataBindingUtil.inflate<PhotoItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.photo_item,
            parent,
            false
        )
        binding.root.setOnClickListener {
            binding.photo?.let {
                photoClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: PhotoItemBinding, item: Photo) {
        binding.photo = item
    }
}
