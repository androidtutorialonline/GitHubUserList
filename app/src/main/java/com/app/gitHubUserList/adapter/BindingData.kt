package com.app.gitHubUserList.adapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.app.gitHubUserList.R

@BindingAdapter("imageFromUrl")
fun AppCompatImageView.bindImageFromUrl(url: String?) {
    if (!url.isNullOrEmpty()) {
        load(url)
    }
}

@BindingAdapter("isCheckFav")
fun bindIsCheckFav(view: AppCompatImageView, isCheckFav: Boolean) {
    if (isCheckFav) {
        view.load(R.drawable.ic_baseline_favorite_24)
    } else {
        view.load(R.drawable.ic_baseline_favorite_border_24)
    }
}
