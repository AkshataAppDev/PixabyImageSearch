package com.example.pixabyimagesearch.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.pixabyimagesearch.R
import com.example.pixabyimagesearch.repository.Status

@BindingAdapter("imageUrl")
fun showPhoto(imageView: ImageView, url: String?) {

    val imageUri = url?.toUri()?.buildUpon()?.scheme("https")?.build()

    Glide.with(imageView.context)
        .load(imageUri)
        .placeholder(R.drawable.ic_image_placeholder)
        .error(R.drawable.ic_broken_image)
        .into(imageView)
}

@BindingAdapter("tagLabels")
fun setTagsLabels(textView: TextView, tags: String?) {
    textView.text = textView.context.getString(R.string.tags,tags)

}

@BindingAdapter("photoCredit")
fun setUserName(textView: TextView, username: String?) {
        textView.text = textView.context.getString(R.string.from,username)
}

@BindingAdapter("commentsCount")
fun setCommentsString(textView: TextView, commentsCount: Int?) {
    textView.text = commentsCount.toString()
}

@BindingAdapter("favouritesCount")
fun setFavString(textView: TextView, favCount: Int?) {
    textView.text = favCount.toString()
}

@BindingAdapter("likeCount")
fun setLikesString(textView: TextView, likeCount: Int?) {
    textView.text = likeCount.toString()
}

@BindingAdapter("searchStatus")
fun bindStatus(statusImageView: ImageView, status: Status?) {

    // to show status to user in case of data being downloaded/ offline/ error
    when (status) {
        Status.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }

        Status.SUCCESS -> {
            statusImageView.visibility = View.GONE
        }

       Status.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("isVisible")
fun showOrHideView(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}



    
