package com.example.neighbors.adapters

import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.neighbors.R

object NeighborBindingAdapter {
    @BindingAdapter("app:avatar")
    @JvmStatic
    fun bindImage(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.ic_baseline_delete_outline_24)
            .error(R.drawable.ic_baseline_delete_outline_24)
            .skipMemoryCache(false)
            .into(imageView)
    }

    @BindingAdapter("app:favorite")
    @JvmStatic
    fun likeUpdate(imageButton: ImageButton, item: Boolean) {
        if (item) {
            imageButton.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            imageButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }
}
